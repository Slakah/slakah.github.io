import { fetchData, storeInCache } from './api';
const data = await fetchData();
await storeInCache(data);