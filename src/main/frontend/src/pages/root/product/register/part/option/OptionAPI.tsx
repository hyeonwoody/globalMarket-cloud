import axios from "axios";
import {My} from '../../../../../../configuration/web/webConfig';
const my = new My();



export function OptionAxios(resultCallback: (data: any) => void, data : string[]) {
    let ret : boolean = false;
    axios({
        url: `product/option/naver/standard-options?category=${encodeURIComponent(data.join(">"))}`,
        method: 'get',
        baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
        withCredentials: true,
    }).then(function (response) {
        console.log("AAA");
        console.log(response);
        console.log(response.data);
        if (response.status == 200) {
            resultCallback(response.data);

            ret = response.data.useStandardOption;
            console.log("bbbb"+ret);
        }
        console.log("ccc"+ret);
        return ret;
    });
    console.log("bbbbb"+ret);
    return ret;
}
export default OptionAxios;