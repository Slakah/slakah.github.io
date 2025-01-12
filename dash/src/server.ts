import express, { Request, Response } from 'express';
import { api } from './api';
import cors from 'cors';
import _ from 'lodash';
import { fileURLToPath } from 'url';
import path from 'path';

const app = express();
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.use(cors());

// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, '../dist')));

app.get("/api/data", async (_req: Request, res: Response) => {
  res.json(await api());
});


// Fallback for unknown routes (useful for Single Page Applications)
app.get('*', (_: Request, res: Response) => {
  res.sendFile(path.join(__dirname, '../public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`Static server is running on http://localhost:${PORT}`);
});
