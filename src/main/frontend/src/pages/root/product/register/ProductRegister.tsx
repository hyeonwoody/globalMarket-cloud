import React, {ChangeEvent, useEffect, useState} from 'react';
import ProductAxios, {CallbackStrategy, ErrorResponse, ProductImage, ProductOption, RegisterState} from "../ProductAPI";
import {platformList} from "../../../../configuration/platform";
import Modal from "../../part/Modal";
import ProductRegisterAPI from "./ProductRegisterAPI";
import Category from "./part/Category";
import Image from "./part/images/Image"
import {AxiosResponse} from "axios";
import Keyword from "./part/Keyword";



const ProductRegister: React.FC = () => {
    const [category, setCategory] = useState(new Map<string, string[]>());
    const initialState : RegisterState = {
        additionalInfoList: [],
        category: [],
        detailContent: "",
        name: "",
        platform: 0,
        salePrice: 0,
        stockQuantity: 0,
        url: "",
        images: {
            representativeImage: { url: "" }, // Default URL
            optionalImages: []
        },
        pageTitle : "",
        metaDescription : "",
        tagList: [],
    };
    const [input, setInput] = useState<RegisterState>(initialState);
    const [inputImageCache, setInputImageCache] = useState<ProductImage>();
    const [inputImages, setInputImages] = useState<ProductImage>({
        representativeImage: { url: "" }, // Default URL
        optionalImages: []
    },);
    const [inputPageTitle, setInputPageTitle] = useState<string>("");
    const [inputMetaDescription, setInputMetaDescription] = useState<string>("");
    const [inputTagList, setInputTagList] = useState<string[]>([]);




    const [platformState, setPlatform] = useState ("네이버");
    const [isValidUrl, setValidUrl] = useState (false);
    const [dropdown, setDropdown] = useState (false);
    const [showCategory, setShowCategory] = useState (false);
    const [showURLModal, setShowURLModal] = useState(false);
    const [showInfo, setShowInfo] = useState(false);
    const [additionalInfoList, setAdditionalInfo] = useState<string[]>([]);

    const inputClassName = `appearance-none block w-full bg-gray-200 text-gray-700 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white ${isValidUrl ? '' : 'border border-red-500'}`;

    const [showResultModal, setShowResultModal] = useState(false);
    const [confirmResponse, setConfirmResult] = useState<string>();

    const toggleDropdown = () => {
        setDropdown(!dropdown);
    }


    const ImageCallback = (strategy : keyof CallbackStrategy, index? : number) => {
        switch (strategy){
            case "Reset":
                setInputImages(inputImageCache as ProductImage);
                break;
            case "Delete":
                if (index as number === 0 && inputImages?.optionalImages.length !== 0){
                    setInputImages ((prevState :ProductImage) => {
                        inputImages.representativeImage.url = prevState.optionalImages[0].url;
                        let updatedImages = prevState.optionalImages.filter((_, idx) =>idx !== 0);
                        return {
                            ...prevState,
                            ["optionalImages"]: updatedImages,
                        }
                    })
                }
                else if(index as number > 0)
                {
                    setInputImages((prevState : ProductImage) => {
                        let updatedImages = prevState.optionalImages.filter((_, idx) => idx !== (index as number)-1);
                        return {
                            ...prevState,
                            ["optionalImages"]: updatedImages,
                        }
                    });
                }
                break;
            case "Switch":
                setInputImages((prevState : ProductImage) => {
                    const {representativeImage, optionalImages} = prevState;
                    const updatedOptionalImages = [...optionalImages];
                    if (index !== undefined && 0 <= index && index < optionalImages.length){
                        const updatedRepresentativeImage = updatedOptionalImages[index];
                        updatedOptionalImages[index] = representativeImage;
                        return {
                            ...prevState,
                            representativeImage: updatedRepresentativeImage,
                            optionalImages: updatedOptionalImages,
                        };
                    }
                    return prevState;
                });
                break;
        }
    }

    const CategoryCallback = (result : string, level : number) => {
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

    const KeywordDeleteCallback = (index : number) => {
        setInputTagList((prevState : string[])=>{
            const tmp = prevState.filter((_, idx) => idx !== index);
            console.log("KEYWORD DELETE")
            console.log(tmp);
            return tmp;
        });
    };

    const KeywordCallback = (field: keyof RegisterState, keyword : string, index? : number) => {
        switch (field){
            case "pageTitle":
                setInputPageTitle(keyword);
                break;
            case "metaDescription":
                setInputMetaDescription(keyword);
                break;
            case "tagList":
                if ( 0 <= (index as number) && (index as number) < inputTagList.length){ ////modify tag
                    setInputTagList((prevState) => {
                        const updatedTagList = prevState.map((tag, i) => (i === index ? keyword : tag));
                        return updatedTagList;
                    });
                }
                else { //adding tag
                    setInputTagList((prevState : string[]) => {
                       return [...prevState, keyword];
                    });
                }
                break;
            default :
                console.log("Nothing");
                break;
        }
    }
    const handleInputChange = (field: keyof RegisterState, index? : number) => (
        event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {
        switch (field){
            case "additionalInfoList":
                let aa : number = 0;
                setInput((prevInput) => ({
                    ...prevInput,
                    [field]: prevInput.additionalInfoList.map((info, i) =>
                        i === index ? event.target.value : info
                    ),
                }));
                break;
            case "url":
                setValidUrl(isValid(event.target.value));
                setInput((prevInput) => ({
                    ...prevInput,
                    [field]: event.target.value,
                }));
                break;
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

    const ConfirmResultCallback = (response :  AxiosResponse<any, any>) => {
        if (response.status == 200){
            setConfirmResult("성공적으로 등록 되었습니다.");
            setShowResultModal(true);
        }
        else if (response.status == 400){
            const errorResponse = response.data as ErrorResponse;
            {

            }
            if (errorResponse.invalidInputs != null){
                let message : string = "";
                errorResponse.invalidInputs.map ((input, index) => (
                    message += input.message + "\n"
                ));
                setConfirmResult(message);
            }
            else {
                setConfirmResult(errorResponse.message);
            }
            setShowResultModal(true);
        }
        else if (response.status == 500){
            setConfirmResult("내부 서버 오류");
            setShowResultModal(true);
        }
    }

    const onClickConfirm = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setInput((prevInput : RegisterState) => {
            const updatedInput : RegisterState =
                {...prevInput,
                    ["pageTitle"]: inputPageTitle as string,
                    ["metaDescription"]: inputMetaDescription as string,
                    ["tagList"]: inputTagList as string[],
                    ["images"]:inputImages as ProductImage,
            };
            if (isValidUrl){
                ProductAxios(ConfirmResultCallback, "register/confirm", updatedInput);
            }
            else {
                setShowURLModal(true);
            }
            return updatedInput;
        });
    }

    const parseResultCallback = (data : RegisterState) => {
        setInput((prevInput) => ({
            ...prevInput,
            ["name"]: data.name,
            ["detailContent"]:data.detailContent,
            ["salePrice"]:data.salePrice,
            ["stockQuantity"]:data.stockQuantity,
            ["additionalInfoList"]:data.additionalInfoList,
        }));
        setInputImages(data.images);
        setInputImageCache(data.images);

        setInputPageTitle(data.pageTitle);
        setInputMetaDescription(data.metaDescription);
        setInputTagList(data.tagList);

        setAdditionalInfo(data.additionalInfoList);
        setShowInfo(true);
        var preview = document.getElementById('preview');
        if (preview != null)
            preview.innerHTML = data.detailContent;
        console.log("DDDD");
        console.log(input);
    }

    const onClickParse = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        console.log("EEEE   ");
        setInput((prevInput : RegisterState) => {
            const updatedInput = {...prevInput};
            console.log(updatedInput);
            if (isValidUrl){
                ProductAxios(parseResultCallback, "register/information", updatedInput);
            }
            else {
                setShowURLModal(true);
            }
            return updatedInput;
        });
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
                        {showCategory && <Category category={category} callback={CategoryCallback}/>}
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

                        {showInfo && <Image images={inputImages as ProductImage} callback={ImageCallback}/>}

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

                        {showInfo && <Keyword pageTitle={inputPageTitle} metaDescription={inputMetaDescription} tagList={inputTagList} callback={KeywordCallback} deleteCallback={KeywordDeleteCallback}/>}

                        {showInfo && <div className="flex flex-wrap -mx-3 mb-2" id={"product-additionalInfo"}>
                            {additionalInfoList?.map((info, index) => (
                                <div className="md:w-1/2 px-3 mb-0 md:mb-2" id={"product-price"} key={index}>
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
                            <div className="flex flex-wrap -mx-3 mb-2" id={"product-detail"}>
                                <div className="px-3 mb-0 md:mb-2" id={"product-price"}>
                                    <label
                                        className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
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
                            </div>
                        }

                        {showInfo ?
                            <div>

                                <div className="fixed bottom-0 right-0 p-6 floating-buttons">
                                    <button
                                        className="bg-gray-800 text-white rounded-full w-10 h-10 mb-2 flex items-center justify-center"
                                        onClick={onClickParse}><p>다시</p>
                                    </button>
                                    <button
                                        className="bg-green-500 hover:bg-green-700 text-white rounded-full font-bold w-10 h-10 mx-auto block"
                                        onClick={onClickConfirm}>
                                        확정
                                    </button>
                                </div>
                            </div> :
                            <button
                                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                                onClick={onClickParse}>
                            분석
                            </button>}
                    </div>


                </form>


            </div>
            <Modal show={showURLModal} onClose={() => setShowURLModal(false)}>
                <p>유효한 URL을 입력해주세요.</p>
                <button onClick={() => setShowURLModal(false)}>Close</button>
            </Modal>
            <Modal show={showResultModal} onClose={() => setShowResultModal(false)}>
                <p>{confirmResponse}</p>
                <button onClick={() => setShowResultModal(false)}>Close</button>
            </Modal>

        </div>

    )
        ;
            }

            export default React.memo(ProductRegister);