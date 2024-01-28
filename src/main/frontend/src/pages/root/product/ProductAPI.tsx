import axios from 'axios';
import {My} from '../../../configuration/web/webConfig';
import {Platform} from '../../../configuration/platform';
const my = new My();

export interface RegisterState {
    platform : Platform,
    url : string,
    name : string,
    detailContent : string
}
export function ProductAxios(resultCallback: (data: any) => void, type : string, payload : any) {
    switch (type){
        case "register":
            axios({
                url: "products/" + type,
                method: 'post',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
                data: payload,
            }).then(function (response) {
                resultCallback(response.data);
            });
            break;
        default:
            break;
    }
}
export default ProductAxios;