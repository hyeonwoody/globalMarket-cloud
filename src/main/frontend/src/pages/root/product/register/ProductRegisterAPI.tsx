import axios from 'axios';
import {My} from '../../../../configuration/web/webConfig';
import {Platform} from '../../../../configuration/platform';
import {ProductOptionSimple, ProductOptionStandard} from "./part/option/Option";

const my = new My();

export interface InvalidInput {
    name: string,
    type: string,
    message: string,
}
export interface ErrorResponse{
    code: string;
    message: string;
    timestamp: string;
    invalidInputs: InvalidInput[] | undefined,
}

export interface CallbackStrategy {
    Create : 0,
    Delete : 1,
    Switch : 2,
    Reset : 3,
}
export interface ProductImage {
    representativeImage : {url : string},
    optionalImages: {url: string}[]
}

export interface RegisterState {
    platform : Platform,
    category : string[],
    url : string,
    name : string,
    detailContent : string
    salePrice : number,
    stockQuantity : number
    additionalInfoList : string[],
    images : ProductImage,
    pageTitle : string,
    metaDescription : string,
    tagList : string[],
    optionType : number,
    option : ProductOptionSimple[] | ProductOptionStandard | undefined
}

export function ProductRegisterAxios(resultCallback: (data: any) => void, type : string, data : RegisterState) {
    console.log ("Axios"+data);
    switch (type){

        case "information":
            console.log ("FFFFFF, "+ data)
            console.log("GGG");
            console.log(data);
            axios({
                url: `product-register/${type}?url=${encodeURIComponent(data.url)}&category=${encodeURIComponent(data.category.join(">"))}`,
                method: 'get',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
            }).then(function (response) {
                if (response.status == 200)
                    resultCallback(response.data);
            });
            break;
        case "confirm":
            //const {images, ...dataWitoutImages} = data

            axios({
                url: "product-register/" + type,
                method: 'post',
                baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
                withCredentials: true,
                data: data,
            }).then(function (response) {
                resultCallback(response);
            }).catch(function (error) {
                resultCallback(error.response);
            });
            break;
        default:
            break;
    }
}
export default ProductRegisterAxios;