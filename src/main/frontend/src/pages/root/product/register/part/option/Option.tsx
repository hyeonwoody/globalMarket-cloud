import React, {ChangeEvent, useEffect, useState} from "react";
import OptionAxios from "./OptionAPI";
import OptionStandard from "./OptionStandard";

interface OptionProps {
    fetchData : boolean,
    category : string[],
    callback : (value : ProductOptionSimple[] | ProductOptionStandard | undefined) => void
}

export interface StandardOptionAttribute {
    attributeId: number;
    attributeValueId: number;
    attributeValueName: string;
    attributeColorCode: string;
    imageUrls: string[];
    usable: boolean | undefined,
}
export interface StandardOptionCategoryGroup {
    attributeId: number;
    attributeName: string;
    groupNames: string;
    imageRegistrationUsable: boolean;
    realValueUsable: boolean;
    optionSetRequired: boolean;
    standardOptionAttributes: StandardOptionAttribute[];
}

// Interface for StandardOptionAttribute

export interface ProductOptionStandard {
    useStandardOption : boolean,
    standardOptionCategoryGroups : StandardOptionCategoryGroup[],
}

export interface ProductOptionSimple {
    groupName: string,
    name: string
}

export interface ProductOption{

}

function Option (props: OptionProps) {
    const [optionUse, setOptionUse] = useState <number>(1);
    const [optionList, setOptionList] = useState <ProductOptionSimple[]> ([
        {
            groupName: "",
            name : ""
        }
    ]);
    const [standardList, setStandardList] = useState <ProductOptionStandard>();
    useEffect(() => {
        getStandaradInfo();
    }, [props.category, props.fetchData]);

    const getStandaradInfo = async () => {
        OptionAxios(AxiosCallback, props.category);
    }
    const fetchData = async () => {
        console.log("Option Fetch : ", optionUse);
        if (props.fetchData && (optionUse === 0)) {
            console.log(optionList);
            props.callback(optionList);
        }
        else if (props.fetchData && (optionUse === 1)){
            props.callback(undefined);
        }
        else if (props.fetchData && (optionUse === 2)){
            console.log(standardList);
            props.callback(standardList);
        }
    };

    const AxiosCallback = (data : ProductOptionStandard) => {
        console.log("Option : ",data.useStandardOption);
        console.log(data);
        if (data.useStandardOption) {
            setStandardOptions(data);
        }
        setStandardAble(data.useStandardOption);
    }

    const [standardAble, setStandardAble] = useState<boolean>();
    const [standardOption, setStandardOptions] = useState<ProductOptionStandard>();

    const setStandardAttributesUsed = (value : ProductOptionStandard) => {
        setStandardList(() => {
            props.callback(value);
            return value;
        });
    }
    console.log("옵션패칭",props.fetchData);

    const handleInputOptionChange = (value : string, index : number) =>
        (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {
        switch (value){
            case "name":
                setOptionList((prevState : ProductOptionSimple[]) => {
                    const updatedOptionList = [...prevState];
                    updatedOptionList[index].name = event.target.value;
                    return (
                        updatedOptionList
                    );
                });
                break;
            case "groupName":
                setOptionList((prevState : ProductOptionSimple[]) => {
                    const updatedOptionList = [...prevState];
                    updatedOptionList[index].groupName = event.target.value;
                    return (
                        updatedOptionList
                    );
                });
                break;

        }

        console.log("HANDLEINPUT")
    }
    interface Use {
        label : string,
        value : number
        checked : boolean
    }
    const uses : Use[] = [
        {label : "설정함" , value : 0, checked : optionUse === 0},
        {label : "설정안함", value : 1, checked : optionUse === 1},
        {label : "표준 옵션", value : 2, checked : optionUse === 2},
    ]



    const handleUseChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value : number = Number(event.target.value);
        switch (value){
            case 0:
                setOptionUse(value);
                break;
            case 1:
                setOptionUse(value);
                break;
            case 2:
                setOptionUse(value);
                break;
        }
    }

    return (
        <div className="Option-Container flex flex-wrap -mx-3 mb-2" id={"product-option"}>
            <div key="0" className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-option-container"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="product-option-label">
                    옵션
                </label>
                <div className="flex gap-7 justify-start" id={"product-option-uses"}>
                    {uses.map((use, index) => (
                        (index == 2) ? standardAble? <div id={"product-option-use-button-container" + index}>
                            <input type="radio" id={"option" + index} name="useGrop" value={use.value}
                                   className="mr-2 appearance-none w-4 h-4 rounded-full bg-gray-300 checked:bg-blue-500 checked:border-blue-500"
                                   onChange={handleUseChange}
                                   checked={optionUse == use.value}/>
                            <label htmlFor="use" className="text-gray-700">{use.label}</label>
                        </div> : null
                            : <div id={"product-option-use-button-container" + index}>
                            <input type="radio" id={"option" + index} name="useGrop" value={use.value}
                                   className="mr-2 appearance-none w-4 h-4 rounded-full bg-gray-300 checked:bg-blue-500 checked:border-blue-500"
                                   onChange={handleUseChange}
                                   checked={optionUse == use.value}/>
                            <label htmlFor="use" className="text-gray-700">{use.label}</label>
                        </div>
                    ))}
                </div>
                {uses[optionUse].label == "설정함" && <div className="grid grid-row-3 gap-2">
                    {optionList?.map((option, index) => (
                        <div className={"flex justify-start gap-2"}>
                            <input
                                className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                id="grid-last-name"
                                type="text"
                                value={option.groupName}
                                placeholder={"옵션명 (예 : 색상)"}
                                onChange={handleInputOptionChange("groupName", index)}/>
                            <input
                                className="appearance-none block w-96 bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                id="grid-last-name"
                                type="text"
                                value={option.name}
                                placeholder={"옵션값 (예 : 검정,빨강)"}
                                onChange={handleInputOptionChange("name", index)}/>
                            <div className="flex justify-center border-2 border-amber-300">
                                <button>삭제</button>
                                <button>추가</button>
                            </div>
                        </div>
                    ))}
                </div>}
                {uses[optionUse].label == "표준 옵션" && standardOption != undefined && <OptionStandard fetchData={props.fetchData} standardOption={standardOption} callback={setStandardAttributesUsed}/>}
            </div>
        </div>
    );
}

export default Option;