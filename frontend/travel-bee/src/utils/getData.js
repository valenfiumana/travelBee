import axios from 'axios';
import alertMessage from './alertMessage';

async function getData(url, settings) {

    try {
        const res = await axios.get(url, settings);
        return res;
    } catch (err) {
        err.response && typeof(err.response.data) === 'string' ? alertMessage(err.response.data) : alertMessage(err.message);
    }
}

export default getData
