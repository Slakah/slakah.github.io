import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// import '@fontsource/roboto/900.css';
import '@fontsource-variable/roboto-condensed';
import './client/index.css'
import App from './client/App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
