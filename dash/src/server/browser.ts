import puppeteer from 'puppeteer-core';

const url = 'http://localhost:3000';

export async function takeScreenshot(outputFile: string): Promise<void> {
  const browser = await puppeteer.connect({
    browserURL: 'http://127.0.0.1:9222',
    defaultViewport: {width: 400, height: 300},
  });
  try {
    const page = (await browser.pages())[0];
    page.setDefaultTimeout(20_000);
    console.log(`saving screenshot of ${url} to ${outputFile}...`)
    await page.goto(url);
    await page.waitForSelector('#loaded', {timeout: 20_000});
    await page.screenshot({path: outputFile, optimizeForSpeed: true});
    console.log('screenshot saved')
  } finally {
    await browser.disconnect();
  }
}
