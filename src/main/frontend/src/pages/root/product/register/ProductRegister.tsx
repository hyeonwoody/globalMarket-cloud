import React, {ChangeEvent, useEffect, useState} from 'react';
import ProductAxios, {RegisterState} from "../ProductAPI";
import Dropdown from "../../part/Dropdown"
import {platformList} from "../../../../configuration/platform";
import Modal from "../../part/Modal";
import ProductRegisterAPI, {ProductRegisterAxios} from "./ProductRegisterAPI";
const ProductRegister: React.FC = () => {
    const [input, setInput] = useState<RegisterState>({platform: 0, detailContent: "", name: "", url: ""});
    const [platformState, setPlatform] = useState ("플랫폼");
    const [isValidUrl, setValidUrl] = useState (false);
    const [dropdown, setDropdown] = useState (false);
    const [showModal, setShowModal] = useState(false);

    const inputClassName = `appearance-none block w-full bg-gray-200 text-gray-700 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white ${isValidUrl ? '' : 'border border-red-500'}`;

    const toggleDropdown = () => {

        setDropdown(!dropdown);

    }

    const handleOption = (result : number) => {
        setPlatform(platformList[result])
        toggleDropdown();
        setInput((prevInput) => ({
            ...prevInput,
            ["platform"]: result,
        }));

        const  generateCategory = (data : any) => {
            interface CategoryNaver{
                wholeCategoryName: string,
                id: string,
                name: string,
                last: boolean
            }

            let categoryNaverList: CategoryNaver[] = data as CategoryNaver[];
            categoryNaverList.forEach((categoryNaver) => {
                console.log(categoryNaver.wholeCategoryName);
            });

        }

        ProductRegisterAPI(generateCategory, input.platform);
    }

    const isValid = (url : string) => {
        const urlRegex = /^https?:\/\/(?:www\.)?ko\.aliexpress\.com\/item\/\d+\.html$/;
        return urlRegex.test(url);
    }

    const handleInputChange = (field: keyof RegisterState) => (
        event: ChangeEvent<HTMLInputElement>
    ) => {
            setInput((prevInput) => ({
                ...prevInput,
                [field]: event.target.value,
            }));
        if (field === "url"){
            setValidUrl (isValid(event.target.value));
        }
        console.log (input);

    };


    const generateOptions = () => {
        const options: any[] = [];

        platformList.forEach((item, index) => {
            options.push(
                <button
                    key={index}
                    onClick={() => handleOption(index)}
                    className="block px-4 py-2  hover:bg-gray-100  dark:hover:text-black "
                >
                    {item}
                </button>
            );
        });

        return options;
    }
    const dropdownResultCallback = (result : number) => {
        console.log("dropdown");
    }

    const submitResultCallback = (data : number) => {

    }

    const onClickSubmit = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (isValidUrl)
            ProductAxios(submitResultCallback, "register", input);
        else {
            console.log ("모달")
            setShowModal(true);
        }
    }

    return (
        <div className="Container">


            <div className="p-4 sm:ml-64">

                <form className="w-full">
                    <div className="w-full p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">
                        <div className={"button-Container -mx-3 relative"} id={"product-platform"}>
                            <div className="w-full md:w-full px-3 mb-6 md:mb-3">
                                <label className="block text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-url">
                                    플랫폼
                                </label>
                                <button id="dropdownHoverButton"
                                        onClick={toggleDropdown}
                                        className="text-white bg-green-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                                        type="button">{platformState}
                                    <svg className="w-2.5 h-2.5 ms-3" aria-hidden="true"
                                         xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                        <path stroke="currentColor"
                                              d="m1 1 4 4 4-4"/>
                                    </svg>
                                </button>

                                {dropdown && (
                                    <div
                                        className="absolute z-10 top-full left-0 mt-1 bg-green-400 rounded-lg shadow w-44 dark:bg-gray-700">
                                        {generateOptions()}


                                    </div>

                                )}
                            </div>
                        </div>
                        <div className="flex flex-wrap -mx-3 mb-2" id={"product-url"}>
                            <div className="w-full md:w-full px-3 mb-6 md:mb-3">
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-url">
                                    URL
                                </label>
                                <input
                                    className={inputClassName}
                                    id="grid-product-url" type="text"
                                    placeholder="https://ko.aliexpress.com/item/XXXXXX.html"
                                    onChange={handleInputChange("url")}/>
                                {!isValidUrl && <p className="text-red-500 text-xs italic">형식에 맞게 제출해주세요</p>}
                            </div>
                        </div>
                        <div className="flex flex-wrap -mx-3 mb-2" id={"product-info"}>
                            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-name"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-name">
                                    상품 이름
                                </label>
                                <input
                                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    id="grid-last-name"
                                    type="text"
                                    placeholder="도수없는 안경"
                                    onChange={handleInputChange("name")}/>


                            </div>

                            <div className="w-full px-3" id={"product-detail"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-detail">
                                    상품 설명
                                </label>
                                <input
                                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    id="grid-last-name"
                                    type="text"
                                    placeholder="이 상품은 해외구매대행 상품으로 7일 ~ 21일 (주말/공휴일 제외)의 배송기간이 소요됩니다."
                                    onChange={handleInputChange("detailContent")}/>
                            </div>
                        </div>
                        <button
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                            onClick={onClickSubmit}>
                            제출
                        </button>
                    </div>

                </form>


            </div>
            <Modal show={showModal} onClose={() => setShowModal(false)}>
                <p>유효한 URL을 입력해주세요.</p>
                <button onClick={() => setShowModal(false)}>Close</button>
            </Modal>
        </div>

    )
        ;
}

export default React.memo(ProductRegister);