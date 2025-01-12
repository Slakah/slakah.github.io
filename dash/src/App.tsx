import { useState, useEffect } from 'react';
import _ from 'lodash';
import { APIResponse } from './types';
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
  const date = now.toLocaleDateString(locale, { year: 'numeric', month: 'long', day: 'numeric' });
  const busTimes = Object.entries(data?.busTimes ?? {}).map(([line, times]) => `${line} (${times})`);
  return (
    <>
      {busTimes.map((row, i) => <div key={i}>{row}</div>)}
      <div className='time'>{time}</div>
      <div className='date'>{date}</div>
    </>
  )
}

export default App
