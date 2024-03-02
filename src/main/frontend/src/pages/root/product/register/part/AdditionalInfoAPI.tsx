import axios from "axios";
import {My} from '../../../../../configuration/web/webConfig';
const my = new My();

interface AdditionalInfoProps{
    platform : number,
    category :string[]
}
export function AdditionalInfoAxios(resultCallback: (data: any) => void, data : AdditionalInfoProps) {
    switch (data.platform){
        case 0:
            axios({
                url: `products/register/information-additional?category=${encodeURIComponent(data.category.join(">"))}`,
                method: 'get',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
            }).then(function (response) {
                if (response.status == 200)
                    resultCallback(response.data);
            });
            break;
        default:
            break;
    }
}
export default AdditionalInfoAxios;