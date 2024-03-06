import React, {ChangeEvent, useState} from 'react';
    import ProductRegisterAPI, {CallbackStrategy, ErrorResponse, ProductImage, ProductOption, RegisterState} from "./ProductRegisterAPI";
import Modal from "../../part/Modal";
import ProductRegisterCategoryAPI from "./ProductRegisterCategoryAPI";
import Category from "./part/category/Category";
import Image from "./part/images/Image"
import {AxiosResponse} from "axios";
import Keyword from "./part/Keyword";
import Option from "./part/Option";
import Platform from "./part/Platform";
import AdditionalInfo from "./part/AdditionalInfo";




const ProductRegister: React.FC = () => {
    const [category, setCategory] = useState<Map<string, string[]>>(new Map<string, string[]>());
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
        optionList: undefined
    };
    const [input, setInput] = useState<RegisterState>(initialState);
    const [inputCategory, setInputCategory] = useState<string[]> ([]);
    const [inputAdditionalInfoList, setInputAdditionalInfoList] = useState<string[]>();

    const [inputImageCache, setInputImageCache] = useState<ProductImage>();
    const [inputImages, setInputImages] = useState<ProductImage>({
        representativeImage: { url: "" }, // Default URL
        optionalImages: []
    },);
    const [inputPageTitle, setInputPageTitle] = useState<string>("");
    const [inputMetaDescription, setInputMetaDescription] = useState<string>("");
    const [inputTagList, setInputTagList] = useState<string[]>([]);
    const [inputOptionList, setInputOptionList] = useState<ProductOption[] | undefined>();

    const [isFetchingData, setFetchingData] = useState<boolean>(false);


    const [isValidUrl, setValidUrl] = useState (false);
    const [showURLModal, setShowURLModal] = useState(false);
    const [showInfo, setShowInfo] = useState(false);


    const inputClassName = `appearance-none block w-full bg-gray-200 text-gray-700 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white ${isValidUrl ? '' : 'border border-red-500'}`;

    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [showResultModal, setShowResultModal] = useState(false);
    const [confirmResponse, setConfirmResult] = useState<string>();

    const PlatformCallback = (platform : number) => {
        const  generateCategory = (data : any) => {
            const categoryMap = new Map();
            switch (platform){
                case 0:
                    console.log("00");
                    Object.entries(data).map(([key , value]) =>{
                        categoryMap.set (key, value);
                    });
                    console.log (categoryMap);
                    setCategory(categoryMap);
                    break;
                default:
                    //this will not be executed, since ProductRegisterAPI never call to here, when platform != 0
                    setCategory(categoryMap);
                    break;
            }
        }
        ProductRegisterCategoryAPI(generateCategory, platform);
    }

    const CategoryCallback = (value : string[]) => {
        setInputCategory(value);
    }

    const AdditionalInfoCallback = (value : string[]) => {
        setInputAdditionalInfoList(value);
    }

    const OptionCallback = (value : ProductOption[] | undefined) => {
        console.log("OptionCallback");
        setInputOptionList(value);
    }

    const ImageCallback = (value : ProductImage) => {
        setInputImages(value);
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
                        return prevState.map((tag, i) => (i === index ? keyword : tag));
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
    const handleTest =  (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setFetchingData(true);
    }
    const handleInputChange = (field: keyof RegisterState) => (
        event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {
        switch (field){
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
                errorResponse.invalidInputs.map ((input) => (
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
    const onNoConfirmModal = () => {
        setShowConfirmModal(false);
        setFetchingData(false);
    }
    const onYesConfirmModal = () => {
        setShowConfirmModal(false);
        setInput((prevInput : RegisterState) => {
            const updatedInput : RegisterState =
                {...prevInput,
                    ["category"] : inputCategory as string[],
                    ["additionalInfoList"]: inputAdditionalInfoList as string[],
                    ["pageTitle"]: inputPageTitle as string,
                    ["metaDescription"]: inputMetaDescription as string,
                    ["tagList"]: inputTagList as string[],
                    ["images"]:inputImages as ProductImage,
                    ["optionList"]:inputOptionList
                };
            if (isValidUrl){
                ProductRegisterAPI(ConfirmResultCallback, "confirm", updatedInput);
            }
            else {
                setShowURLModal(true);
            }
            return updatedInput;
        });
        setFetchingData(false);
    }

    const onClickConfirm =  (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setFetchingData(true);
        setShowConfirmModal(true);
    }

    const parseResultCallback = (data : RegisterState) => {
        setInput((prevInput) => ({
            ...prevInput,
            ["name"]: data.name,
            ["detailContent"]:data.detailContent,
            ["salePrice"]:data.salePrice,
            ["stockQuantity"]:data.stockQuantity,
        }));
        setInputImages(data.images);
        setInputImageCache(data.images);

        setInputPageTitle(data.pageTitle);
        setInputMetaDescription(data.metaDescription);
        setInputTagList(data.tagList);

        setShowInfo(true);
        let preview = document.getElementById('preview');
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
                ProductRegisterAPI(parseResultCallback, "information", updatedInput);
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
                        {<Platform callback={PlatformCallback} />}
                        {<Category category={category} callback={CategoryCallback}/>}
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

                        {showInfo && <Image fetchData={isFetchingData} images={inputImages as ProductImage} callback={ImageCallback}/>}

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
                        <button
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                            onClick={handleTest}>
                            테스트
                        </button>
                        {showInfo && <Option fetchData={isFetchingData} category={inputCategory} callback={OptionCallback}/>}

                        {showInfo && <Keyword pageTitle={inputPageTitle} metaDescription={inputMetaDescription}
                                              tagList={inputTagList} callback={KeywordCallback}
                                              deleteCallback={KeywordDeleteCallback}/>}
                        {showInfo && <AdditionalInfo fetchData={isFetchingData} platform={input.platform} category={inputCategory} callback={AdditionalInfoCallback}/>}

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
                                </div>:
                            <button
                                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block "
                                onClick={onClickParse}>
                                분석
                            </button>}
                    </div>


                </form>


            </div>
            <Modal show={showURLModal} onClose={() => setShowURLModal(true)}>
                <p>유효한 URL을 입력해주세요.</p>
                <button onClick={() => setShowURLModal(false)}>Close</button>
            </Modal>
            <Modal show={showConfirmModal} onClose={() => setShowConfirmModal(false)}>
                <p>제품 등록합니다.</p>
                <div className={"flex justify-center gap-3"}>
                <button onClick={() => onYesConfirmModal()}>Yes</button>
                <button onClick={() => onNoConfirmModal()}>no</button>
                </div>
            </Modal>
            <Modal show={showResultModal} onClose={() => setShowResultModal(true)}>
                <p>{confirmResponse}</p>
                <button onClick={() => setShowResultModal(false)}>Close</button>
            </Modal>

        </div>

    )
        ;
}
export default React.memo(ProductRegister);