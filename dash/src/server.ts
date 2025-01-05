import express, { Request, Response } from 'express';
import { fileURLToPath } from 'url';
import path from 'path';

const app = express();
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);


// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, '../dist')));

// Fallback for unknown routes (useful for Single Page Applications)
app.get('*', (_: Request, res: Response) => {
  res.sendFile(path.join(__dirname, '../public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`Static server is running on http://localhost:${PORT}`);
});
