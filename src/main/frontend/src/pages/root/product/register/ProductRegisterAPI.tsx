import axios from "axios";
import {My} from '../../../../configuration/web/webConfig';
const my = new My();

export function ProductRegisterAxios(resultCallback: (data: any) => void, platform : number) {
    switch (platform){
        case 0:
            axios({
                url: "categories/" + "naver",
                method: 'get',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
            }).then(function (response) {
                resultCallback(response.data);
            });
            break;
        default:
            break;
    }
}
export default ProductRegisterAxios;