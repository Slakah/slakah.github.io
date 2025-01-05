import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [now, setNow] = useState(new Date());

  useEffect(() => {
    const timer = setInterval(() => setNow(new Date()), 1000);
    return () => clearInterval(timer);
  }, []);

  const locale = 'en-GB';
  const time = now.toLocaleTimeString(locale, { hour: '2-digit', minute: '2-digit', hour12: false});
  const date = now.toLocaleDateString(locale, { year: 'numeric', month: 'long', day: 'numeric' });
  return (
    <>
      <div className='time'>{time}</div>
      <div className='date'>{date}</div>
    </>
  )
}

export default App
