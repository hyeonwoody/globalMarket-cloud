import axios from 'axios';
import {My} from '../../configuration/web/webConfig';

const my = new My();
export function RootAxios(resultCallback: (data: any) => void) {

    axios({
        url: "test/ia",
        method: 'post',
        baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
        withCredentials: true,
        data: {
            // Include your data here
            // Example:
            key1: 'value1',
            key2: 'value2',
        },
    }).then(function (response) {
        resultCallback(response.data);
    });


}
export default RootAxios;