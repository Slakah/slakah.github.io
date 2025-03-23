import express, { NextFunction, Request, Response } from 'express';
import { api, fetchData, storeInCache } from './server/api';
import { takeScreenshot } from './server/browser';
import { renderDir, adminServices } from './server/admin';
import { promises as fs } from "fs";
import cors from 'cors';
import path from 'path';

const app = express();
const PORT = 3000;

app.use(cors());

const projectDir = path.join(__dirname, '../');
app.get("/admin/files", async (_req: Request, res: Response) => {
  res.send(await renderDir(projectDir, '/admin/files/'));
});
app.get("/admin/files/:path", async (req: Request, res: Response, next: NextFunction) => {
  const absolutePath = path.join(projectDir, req.params.path);
  if ((await fs.stat(absolutePath)).isDirectory()) {
    res.send(await renderDir(absolutePath, '/admin/files/' + req.params.path));
  } else {
    next();
  }
});
app.get("/admin/services", async (_req: Request, res: Response) => {
  res.send(await adminServices());
});
app.use("/admin/files", express.static(projectDir));

// Serve static files from the "public" directory
app.use("/", express.static(path.join(__dirname, '../dist')));

app.get("/api/raw", async (_req: Request, res: Response) => {
  res.json(await fetchData());
});
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
    console.error(`error: ${message} when updating cache`);
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
