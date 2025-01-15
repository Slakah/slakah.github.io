import { takeScreenshot } from './server/browser';

const args = process.argv.slice(2).map(x => x.trim());
const command = args[0];

switch(command) {
  case 'screenshot':
    const output = args[1];
    await takeScreenshot(output);
    process.exit(0);
  default:
    console.error(`error: unsupported command ${command}`);
    process.exit(1);
}