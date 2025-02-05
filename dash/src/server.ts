import express, { Request, Response } from 'express';
import { api, fetchData, storeInCache } from './server/api';
import { takeScreenshot } from './server/browser';
import cors from 'cors';
import _ from 'lodash';
import path from 'path';

const app = express();
const PORT = 3000;

app.use(cors());

// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, '../dist')));

app.get("/api/data", async (_req: Request, res: Response) => {
  res.json(await api());
});

app.post("/api/screenshot", async (_req: Request, res: Response) => {
  try {
    await takeScreenshot('output.png');
    res.json({status: 'ok'});
  } catch (err) {
    const message = (err as Error).message;
    console.error(`error: taking screenshot: ${message}`);
    res.status(400).json({status: 'bad', error: message});
  }

app.post("/api/cache", async (_req: Request, res: Response) => {
  try {
    const data = await fetchData();
    await storeInCache(data);
    res.json({status: 'ok'});
  } catch (err) {
    const message = (err as Error).message;
    console.error(`error: ${message} when taking screenshot`);
    res.status(400).json({status: 'bad', error: message});
  }
});
});

// Fallback for unknown routes (useful for Single Page Applications)
app.get('*', (_: Request, res: Response) => {
  res.sendFile(path.join(__dirname, '../public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`server is running on http://localhost:${PORT}...`);
});
