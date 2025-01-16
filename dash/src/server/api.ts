import { promises as fs } from "fs";
import { APIResponse } from "../types";
import _ from 'lodash';

const stopPointIDs = ['490015052S', '490015052N']

function stopPointArrivalsURL(stopPointID: string) {
  return `https://api.tfl.gov.uk/StopPoint/${stopPointID}/Arrivals`
}

interface StopPointArrival {
  expectedArrival: string;
  lineName: string
}

interface APIData {
  stopPointArrivals: StopPointArrival[];
}

async function fetchStopPointArrivals(stopPointID: string): Promise<StopPointArrival[]> {
  const response = await fetch(stopPointArrivalsURL(stopPointID));
  const json = await response.json() as StopPointArrival[];
  return json.map(({expectedArrival, lineName}) => ({expectedArrival, lineName}));
}

export async function fetchData(): Promise<APIData> {
  return {'stopPointArrivals': await fetchStopPointArrivals(stopPointIDs[0])};
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
    .filter(o => o.times.length != 0)
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
