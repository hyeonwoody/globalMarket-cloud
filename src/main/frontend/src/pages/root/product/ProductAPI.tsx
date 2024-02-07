import axios from 'axios';
import {My} from '../../../configuration/web/webConfig';
import {Platform} from '../../../configuration/platform';
const my = new My();

export interface RegisterState {
    platform : Platform,
    url : string,
    name : string,
    detailContent : string
    salePrice : number,
    stockQuantity : number
}
export function ProductAxios(resultCallback: (data: any) => void, type : string, data : any) {
    switch (type){

        case "register/information":
            console.log ("FFFFFF, "+ data)
            axios({
                url: `products/${type}?url=${encodeURIComponent(data)}`,
                method: 'get',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
            }).then(function (response) {
                resultCallback(response.data);
            });
            break;
        case "register/confirm":
            axios({
                url: "products/" + type,
                method: 'post',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
                data: data,
            }).then(function (response) {
                console.log ("ProductAPI + ProductAxios"+response.status);
                if (response.status == 200)
                    resultCallback(response.data);
            });
            break;
        default:
            break;
    }
}
export default ProductAxios;