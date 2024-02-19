import React, {ChangeEvent, useState} from 'react';
import ProductAxios, {RegisterState, ProductImage} from "../ProductAPI";
import {Platform, platformList} from "../../../../configuration/platform";
import Modal from "../../part/Modal";
import ProductRegisterAPI from "./ProductRegisterAPI";
import Category from "./part/Category";
import Image from "./part/images/Image"


const ProductRegister: React.FC = () => {
    const [category, setCategory] = useState(new Map<string, string[]>());
    const [input, setInput] = useState<RegisterState>({
        additionalInfoList: [],
        category: [],
        detailContent: "",
        name: "", platform: 0, salePrice: 0, stockQuantity: 0,
        url: "",
        images: {
            representativeImage: { url: ""}, // Provide default URL or leave it empty
            optionalImages: []   // Provide default alt text or leave it empty
        },
    });
    const [platformState, setPlatform] = useState ("네이버");
    const [isValidUrl, setValidUrl] = useState (false);
    const [dropdown, setDropdown] = useState (false);
    const [showCategory, setShowCategory] = useState (false);
    const [showModal, setShowModal] = useState(false);
    const [showInfo, setShowInfo] = useState(false);
    const [additionalInfoList, setAdditionalInfo] = useState<string[]>([]);

    const inputClassName = `appearance-none block w-full bg-gray-200 text-gray-700 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white ${isValidUrl ? '' : 'border border-red-500'}`;

    const toggleDropdown = () => {
        setDropdown(!dropdown);
    }
    const ImageCallback = (index : number) => {
        if (index == 0){
            setInput((prevInput: RegisterState) => {
                prevInput.images.representativeImage.url = prevInput.images.optionalImages[0].url;

                let newImageOptional = prevInput.images.optionalImages;
                prevInput.images.optionalImages = newImageOptional.filter((_, idx) => idx !== 0);

                return {
                    ...prevInput,
                    ["images"] : prevInput.images
                };
            });
        }
        else {
            setInput((prevInput: RegisterState) => {
                    let newImageOptional = prevInput.images.optionalImages;
                    prevInput.images.optionalImages = newImageOptional.filter((_, idx) => idx !== index-1);

                    return {
                        ...prevInput,
                        ["images"] : prevInput.images
                    };
            });
        }
        console.log("HHHHH");
        console.log(input);
    }

    const categoryCallback = (result : string, level : number) => {
        if (level < input.category.length){
            setInput((prevInput) => ({
                ...prevInput,
                category: prevInput.category.map((value, index) => index === level ? result : value),
            }));
        }
        else {
            setInput((prevInput) => ({
                ...prevInput,
                category: [...prevInput.category, result],
            }));
        }
        if (category.get(result) == undefined){
            setInput((prevInput) => ({
                ...prevInput,
                category: prevInput.category.slice(0, level+1),
            }));
        }
    }

    const handleOption = (result : number) => {
        setPlatform(platformList[result])
        toggleDropdown();
        setInput((prevInput) => ({
            ...prevInput,
            ["platform"]: result,
        }));

        const  generateCategory = (data : any) => {

            const categoryMap = new Map();

            switch (result){
                case 0:
                    interface CategoryNaver{
                        wholeCategoryName: string,
                            id: string,
                            name: string,
                            last: boolean
                    }
                    let categoryNaverList: CategoryNaver[] = data as CategoryNaver[];
                    Object.entries(data).map(([key , value]) =>{
                        if (Array.isArray(value) && key == "FIRST"){
                            console.log (key, value);
                        }


                        categoryMap.set (key, value);
                    });

                    console.log (categoryMap);
                    setCategory(categoryMap);
                    break;
                default:
                    break;
            }

            // categoryNaverList.forEach((categoryNaver) => {
            //     console.log(categoryNaver.wholeCategoryName, categoryNaver.name);
            // });
        }
        setShowCategory(true);
        ProductRegisterAPI(generateCategory, input.platform);
    }

    const isValid = (url : string) => {
        const urlRegex = /^https?:\/\/(?:www\.)?ko\.aliexpress\.com\/item\/\d+\.html$/;
        return urlRegex.test(url);
    }

    const handlePasteChange = (field: keyof RegisterState) => (event: React.ClipboardEvent<HTMLInputElement>) => {
        setInput((prevInput) => ({
            ...prevInput,
            [field]: event.clipboardData.getData('text')
        }));
    };

    const handleInputChange = (field: keyof RegisterState, index? : number) => (
        event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {
        switch (field){
            case "additionalInfoList":
                setInput((prevInput) => ({
                    ...prevInput,
                    [field]: prevInput.additionalInfoList.map((info, i) =>
                        i === index ? event.target.value : info
                    ),
                }));
                break;
            case "url":
                setValidUrl(isValid(event.target.value));
            default :
                setInput((prevInput) => ({
                    ...prevInput,
                    [field]: event.target.value,
                }));
                break;
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

    const ResultCallback = (data : number) => {
        console.log ("STATUS : ",data);
    }

    const onClickConfirm = (event : React.MouseEvent<HTMLButtonElement>) => {
        console.log("bbCCCCCCCC");
        event.preventDefault();


        if (isValidUrl){
            console.log("aa");
            ProductAxios(ResultCallback, "register/confirm", input);
        }

        else {
            console.log ("모달")
            setShowModal(true);
        }
    }

    const parseResultCallback = (data : RegisterState) => {
        console.log(data);
        console.log("FFF"+data.name)
        setInput((prevInput) => ({
            ...prevInput,
            ["name"]: data.name,
            ["detailContent"]:data.detailContent,
            ["salePrice"]:data.salePrice,
            ["stockQuantity"]:data.stockQuantity,
            ["additionalInfoList"]:data.additionalInfoList,
            ["images"]:data.images
        }));

        setAdditionalInfo(data.additionalInfoList);
        setShowInfo(true);
        var preview = document.getElementById('preview');
        if (preview != null)
            preview.innerHTML = data.detailContent;
        console.log("DDDD");
        console.log(input);
    }

    const onClickParse = (event : React.MouseEvent<HTMLButtonElement>) => {
        console.log("bbCCCCCCCC");
        event.preventDefault();

        if (isValidUrl){
            console.log("aa");
            ProductAxios(parseResultCallback, "register/information", input);
        }

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
                        {showCategory && <Category category={category} callback={categoryCallback}/>}
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
                                    onPaste={handlePasteChange("url")}
                                    onChange={handleInputChange("url")}/>
                                {!isValidUrl && <p className="text-red-500 text-xs italic">형식에 맞게 제출해주세요</p>}
                            </div>
                        </div>

                        {showInfo && <Image images={input.images} callback={ImageCallback}/>}

                        {showInfo && <div className="flex flex-wrap -mx-3 mb-2" id={"product-info"}>
                            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-name"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-name">
                                    상품 이름
                                </label>
                                <input
                                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    id="grid-last-name"
                                    type="text"
                                    value={input.name}
                                    placeholder="도수없는 안경"
                                    onChange={handleInputChange("name")}/>


                            </div>


                        </div>}

                        {showInfo && <div className="flex flex-wrap -mx-3 mb-2" id={"product-number"}>
                            <div className="md:w-1/2 px-3 mb-0 md:mb-2" id={"product-price"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-name">
                                    상품 가격
                                </label>
                                <input
                                    className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    id="grid-last-name"
                                    type="text"
                                    value={input.salePrice}
                                    placeholder="20000"
                                    onChange={handleInputChange("salePrice")}/>


                            </div>

                            <div className="md:w-1/2 px-3" id={"product-stock"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-detail">
                                    상품 재고
                                </label>
                                <input
                                    className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    name="detailContent"
                                    value={input.stockQuantity}

                                    placeholder="0 ~ 29999"
                                />
                            </div>
                        </div>}

                        {showInfo && <div className="flex flex-wrap -mx-3 mb-2" id={"product-additionalInfo"}>
                            {additionalInfoList?.map((info, index) => (
                                <div className="md:w-1/2 px-3 mb-0 md:mb-2" id={"product-price"}>
                                    <label
                                        className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                        htmlFor="grid-product-name">
                                        {info}
                                    </label>
                                    <input
                                        className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        id="grid-last-name"
                                        type="text"
                                        value={input.additionalInfoList[index]}
                                        placeholder={info}
                                        onChange={handleInputChange("additionalInfoList", index)}/>


                                </div>
                            ))}

                            </div>


                        }

                        {showInfo &&
                            <div className="w-full px-3" id={"product-detail"}>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-product-detail">
                                    상품 설명
                                </label>
                                <textarea
                                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    name="detailContent"
                                    value={input.detailContent}
                                    onChange={handleInputChange("detailContent")}
                                    placeholder="이 상품은 해외구매대행 상품으로 7일 ~ 21일 (주말/공휴일 제외)의 배송기간이 소요됩니다."
                                    rows={Math.max(3, input.detailContent.split('\n').length)}
                                />
                                <div dangerouslySetInnerHTML={{__html: input.detailContent}}/>
                            </div>
                        }

                        {showInfo ?
                            <button
                                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                                onClick={onClickConfirm}>
                                확정
                            </button> :
                            <button
                                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                                onClick={onClickParse}>
                                분석
                            </button>}
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