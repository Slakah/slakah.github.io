
// From: https://www.npmjs.com/package/@akaguny/open-meteo-wmo-to-emoji
// MIT License

// Copyright (c) 2024 akaguny & Contributors

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

export function wmoCode(weatherCode: number, daylight: boolean): { value: string; originalNumericCode: number; description: string } {
  switch (weatherCode) {
    case 0:
      if (daylight) {
        return {
          value: "☀️️",
          originalNumericCode: 0,
          description: "Clear sky",
        };
      } else {
        return {
          value: "🌙",
          originalNumericCode: 0,
          description: "Clear sky",
        };
      }
    case 1:
      if (daylight) {
        return {
          value: "🌤️",
          originalNumericCode: 1,
          description: "Mainly clear",
        };
      } else {
        return {
          value: "🌤️🌙",
          originalNumericCode: 1,
          description: "Mainly clear",
        };
      }
    case 2:
      return {
        value: "☁️",
        originalNumericCode: 2,
        description: "Partly cloudy",
      };
    case 3:
      if (daylight) {
        return { value: "🌥️", originalNumericCode: 3, description: "Cloudy" };
      } else {
        return {
          value: "☁️🌙",
          originalNumericCode: 3,
          description: "Cloudy",
        };
      }
    case 45:
      return { value: "🌫️", originalNumericCode: 45, description: "Fog" };
    case 48:
      return {
        value: "🌫️❄️",
        originalNumericCode: 48,
        description: "Depositing rime fog",
      };
    case 51:
      return {
        value: "🌧️",
        originalNumericCode: 51,
        description: "Drizzle: Light",
      };
    case 53:
      return {
        value: "🌧️",
        originalNumericCode: 53,
        description: "Drizzle: Moderate",
      };
    case 55:
      return {
        value: "🌧️",
        originalNumericCode: 55,
        description: "Drizzle: Dense intensity",
      };
    case 56:
      return {
        value: "🌨️",
        originalNumericCode: 56,
        description: "Freezing Drizzle: Light",
      };
    case 57:
      return {
        value: "🌨️",
        originalNumericCode: 57,
        description: "Freezing Drizzle: Dense intensity",
      };
    case 61:
      return {
        value: "🌦️",
        originalNumericCode: 61,
        description: "Rain: Slight",
      };
    case 63:
      return {
        value: "🌧️",
        originalNumericCode: 63,
        description: "Rain: Moderate",
      };
    case 65:
      return {
        value: "🌧️",
        originalNumericCode: 65,
        description: "Rain: Heavy intensity",
      };
    case 66:
      return {
        value: "🌧️",
        originalNumericCode: 66,
        description: "Freezing Rain: Light",
      };
    case 67:
      return {
        value: "🌧️",
        originalNumericCode: 67,
        description: "Freezing Rain: Heavy intensity",
      };
    case 71:
      return {
        value: "🌨️",
        originalNumericCode: 71,
        description: "Snow fall: Slight",
      };
    case 73:
      return {
        value: "🌨️",
        originalNumericCode: 73,
        description: "Snow fall: Moderate",
      };
    case 75:
      return {
        value: "🌨️",
        originalNumericCode: 75,
        description: "Snow fall: Heavy intensity",
      };
    case 77:
      return {
        value: "🌨️",
        originalNumericCode: 77,
        description: "Snow grains",
      };
    case 80:
      return {
        value: "🌦️",
        originalNumericCode: 80,
        description: "Rain showers: Slight",
      };
    case 81:
      return {
        value: "🌧️🌧️",
        originalNumericCode: 81,
        description: "Rain showers: Moderate",
      };
    case 82:
      return {
        value: "🌧️🌧️🌧️",
        originalNumericCode: 82,
        description: "Rain showers: Violent",
      };
    case 85:
      return {
        value: "🌨️",
        originalNumericCode: 85,
        description: "Snow showers slight",
      };
    case 86:
      return {
        value: "🌨️🌨️",
        originalNumericCode: 86,
        description: "Snow showers heavy",
      };
    case 95:
      return {
        value: "🌩️",
        originalNumericCode: 95,
        description: "Thunderstorm: Slight or moderate",
      };
    case 96:
      return {
        value: "⛈️",
        originalNumericCode: 96,
        description: "Thunderstorm with slight hail",
      };
    case 99:
      return {
        value: "⛈️🌨️",
        originalNumericCode: 99,
        description: "Thunderstorm with heavy hail",
      };
    default:
      return {
        value: "🤷‍♂️",
        originalNumericCode: -1,
        description: "Unknown weather code",
      };
  }
}