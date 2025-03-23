import { useState, useEffect } from 'react';
import { APIResponse } from '../types';
import './App.css';

function App() {
  const [data, setData] = useState<APIResponse | null>(null);
  const [now, setNow] = useState(new Date());
  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`http://${window.location.hostname}:3000/api/data`);
      setData(await response.json() as APIResponse);
    };
    const timer = setInterval(async () => await fetchData(), 10 * 1000);
    fetchData();
    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    const timer = setInterval(() => setNow(new Date()), 1000);
    return () => clearInterval(timer);
  }, []);

  const locale = 'en-GB';
  const time = now.toLocaleTimeString(locale, { hour: '2-digit', minute: '2-digit', hour12: false});
  const date = now.toLocaleDateString(locale, { month: 'short', day: 'numeric' });
  const loaded = data != null;
  const transportTimes = (data?.transportTimes ?? [])
    .map(({label, times}) =>
      <>
        <div className='transport-label'>{label}</div>
        <div className='transport-times'>{times.join(' ')}</div>
      </>);
  return (
    <>
      {loaded ? <div id='loaded'></div> : null}
      <div className='top-container'>
        <div className='date-time'>
          <div className='time'>{time}</div>
          <div className='date'>{date}</div>
        </div>
        <div>
          <div className='temperature'>{data?.weather.temperature}</div>
          <div className='weather'>{data?.weather.weatherLabel}</div>
        </div>
      </div>
      <hr />
      <div className='transport-container'>
        {transportTimes.map((row, i) => <div className='transport-row' key={i}>{row}</div>)}
      </div>
    </>
  )
}

export default App
