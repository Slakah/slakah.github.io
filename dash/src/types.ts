

export interface APIResponse {
  transportTimes: Array<{label: string, times: Array<string>}>;
  weather: {
    temperature: string,
    weatherCode: number,
  }
};