import puppeteer from 'puppeteer-core';
import {sleep} from './utils';

const url = 'http://localhost:3000';

export async function takeScreenshot(outputFile: string): Promise<void> {
  const browser = await puppeteer.connect({
    browserURL: 'http://127.0.0.1:9222',
    defaultViewport: {width: 400, height: 300},
  });
  let context = null;
  try {
    await Promise.all(
      (await browser.browserContexts()).map(async c => {
        if (c.id == null) {
          return await Promise.all((await c.pages()).map(async p => await p.close()));
        } else {
          return await c.close();
        }
      })
    )
    context = await browser.createBrowserContext();
    const page = await context.newPage();
    await page.setCacheEnabled(false);
    page.setDefaultTimeout(30_000);
    console.log(`saving screenshot of ${url} to ${outputFile}...`)
    await page.goto(url);
    await page.waitForSelector('#loaded', {timeout: 20_000});
    await sleep(2_000);
    await page.screenshot({path: outputFile, optimizeForSpeed: true});
    console.log('screenshot saved')
  } finally {
    if (context != null) {
      await context.close();
    }
    await browser.disconnect();
  }
}
