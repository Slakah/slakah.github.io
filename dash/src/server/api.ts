import { promises as fs } from "fs";
import { APIResponse } from "../types";
import config from './config';
import _ from 'lodash';

const stopPointIDs = ['490015052S', '490015052N']

interface StopPointArrival {
  expectedArrival: string;
  lineName: string
}

interface Weather {
  current: {
    temperature_2m: number,
    weather_code: number,
  };
}

interface APIData {
  stopPointArrivals: StopPointArrival[];
  weather: Weather,
}

async function fetchStopPointArrivals(stopPointID: string): Promise<StopPointArrival[]> {
  const response = await fetch( `https://api.tfl.gov.uk/StopPoint/${stopPointID}/Arrivals`);
  const json = await response.json() as StopPointArrival[];
  return json.map(({expectedArrival, lineName}) => ({expectedArrival, lineName}));
}

async function fetchWeather(): Promise<Weather> {
  const {latitude, longitude} = config.location;
  const url = `https://api.open-meteo.com/v1/forecast?latitude=${latitude}&longitude=${longitude}&current=temperature_2m,apparent_temperature,weather_code`;
  const response = await fetch(url);
  const {current: {temperature_2m, weather_code}} = await response.json() as Weather;
  return {current: {temperature_2m, weather_code}};
}


export async function fetchData(): Promise<APIData> {
  return {
    stopPointArrivals: await fetchStopPointArrivals(stopPointIDs[0]),
    weather: await fetchWeather(),
  };
}

export async function fetchDataCached(): Promise<APIData> {
  const cached = await readFromCache();
  const cacheAgeSeconds = (updated: string) => (new Date().getTime() - new Date(updated).getTime()) / 1000;
  if (cached == null || cacheAgeSeconds(cached.updated) > 5 * 60) {
    const data = await fetchData();
    await storeInCache(data);
    return data
  } else {
    return cached.data;
  }
}

function nowISO8601(): string {
  return new Date().toISOString();
}

export async function api(): Promise<APIResponse> {
  const data = await fetchDataCached();
  
  const readableDate = (iso8601: string) => new Date(iso8601).toLocaleTimeString('en-GB', {
    hour: '2-digit',
    minute: '2-digit',
  });
  const {temperature_2m, weather_code} = data.weather.current;
  return {
    transportTimes: Object.values(
      _.groupBy(data.stopPointArrivals, o => o.lineName),
    ).map(data => ({
      label: data[0].lineName,
      times: data
        .map(({expectedArrival}) => expectedArrival)
        .sort()
        .filter(d => d >= nowISO8601())
        .map(readableDate)
        .slice(0, 3)
    }))
    .filter(o => o.times.length != 0),
    weather: {
      temperature: `${Math.round(temperature_2m)}°C`,
      weatherCode: weather_code
    },
  };
}

export async function readFromCache(): Promise<{updated: string, data: APIData} | null> {
  try {
    await fs.access('cache.json');
  } catch {
    return null;
  }
  try {
    return JSON.parse(await fs.readFile('cache.json', 'utf8'));
  } catch (err) {
    const message = (err as Error).message;
    console.error(`error: reading cache.json: ${message}`);
    return null;
  }
}

export async function storeInCache(data: APIData): Promise<void> {
  const entry = {updated: new Date().toISOString(), data};
  await fs.writeFile('cache.json', JSON.stringify(entry), { encoding: "utf-8" });
}
