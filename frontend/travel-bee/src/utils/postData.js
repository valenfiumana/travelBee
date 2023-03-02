import axios from 'axios';
import alertMessage from './alertMessage';

async function postData(url, payload, settings, errMessage) {
    const errorMessage = errMessage;
    try {
        const res = await axios.post(url, payload, settings);
        return res;
    } catch (err) {
        if (errorMessage) {
            alertMessage(errorMessage);
        } else {
            err.response && typeof(err.response.data) === 'string' ? alertMessage(err.response.data) : alertMessage(err.message);
        }
    }
}

export default postData
