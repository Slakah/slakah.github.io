import type { PathLike } from "fs";
import { promises as fs } from "fs";
import path from 'path';
import exec from './exec';

export async function renderDir(dirPath: PathLike, pathPrefix: string): Promise<string> {
  const files = await fs.readdir(dirPath);
  const fileLinks = files
    .map(file => 
      `<li><a href="${path.join(pathPrefix, file)}">${file}</a></li>`
    ).join('');
  return `
    <html>
      <head>
        <title>Directory Listing</title>
      </head>
      <body>
        <h1>Files in Directory</h1>
        <ul>
          ${fileLinks}
        </ul>
      </body>
    </html>
  `;
}

async function execSafe(cmd: string): Promise<string> {
  try {
    const out = await exec(cmd);
    return out.stdout;
  } catch (err) {
    const error = (err as Error).message;
    return `error: ${error}`;
  }
}

export async function adminServices(): Promise<string> {
  const services = [{label: 'Dash', name: 'dash'}, {label: 'Headless Chrome', name: 'headless-chrome'}];
  const rows = await Promise.all(services.map(async s => {
    const status = await execSafe(`systemctl status ${s.name}`);
    return {...s, status};
  }))
  return `
    <html>
      <head>
        <title>Services</title>
      </head>
      <body>
        <h1>Services</h1>
        ${rows.map(({label, status}) => 
          `<h2>${label}</h2><div style="white-space: pre;">${status}</div>`
        ).join()}
      </body>
    </html>
  `;
}