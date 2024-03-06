import React, {ChangeEvent, useEffect, useState} from "react";
import {ProductOption} from "../../ProductRegisterAPI"
import OptionAxios from "./OptionAPI";

interface OptionProps {
    fetchData : boolean,
    category : string[],
    callback : (value : ProductOption[] | undefined) => void
}

interface StandardOptionCategoryGroup {
    attributeId: number;
    attributeName: string;
    groupNames: string;
    imageRegistrationUsable: boolean;
    realValueUsable: boolean;
    optionSetRequired: boolean;
    standardOptionAttributes: StandardOptionAttribute[];
}

// Interface for StandardOptionAttribute
interface StandardOptionAttribute {
    attributeId: number;
    attributeValueId: number;
    attributeValueName: string;
    attributeColorCode: string;
    imageUrls: string[];
}
interface StandardOption {
    useStandardOption : boolean,
    standardOptionCategoryGroups : StandardOptionCategoryGroup[],
}

function Option (props: OptionProps) {
    const [optionUse, setOptionUse] = useState <boolean>(false);
    const [optionList, setOptionList] = useState <ProductOption[]> ([
        {
            groupName: "",
            name : ""
        }
    ]);

    useEffect(() => {
        OptionAxios(AxiosCallback, props.category);
    }, [props.category]);
    const AxiosCallback = (data : StandardOption) => {
        if (data.useStandardOption)
            setStandardOptions(data);
        setStandardAble(data.useStandardOption);
    }

    const [standardAble, setStandardAble] = useState<boolean>();
    const [standardOption, setStandardOptions] = useState<StandardOption>();
    if (props.fetchData && optionUse) {
        props.callback(optionList);
    }
    else if (props.fetchData && (!optionUse)){
        props.callback(undefined);
    }

    const handleInputOptionChange = (value : string, index : number) =>
        (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {
        switch (value){
            case "name":
                setOptionList((prevState : ProductOption[]) => {
                    const updatedOptionList = [...prevState];
                    updatedOptionList[index].name = event.target.value;
                    return (
                        updatedOptionList
                    );
                });
                break;
            case "groupName":
                setOptionList((prevState : ProductOption[]) => {
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
        {label : "설정함" , value : 0, checked : optionUse},
        {label : "설정안함", value : 1, checked : !optionUse},
    ]

    const handleUseChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value : number = Number(event.target.value);
        switch (value){
            case 0:
                setOptionUse(true);
                break;
            case 1:

                setOptionUse(false);
                break;
        }
    }

    return (
        <div className="Option-Container flex flex-wrap -mx-3 mb-2" id={"product-option"}>
            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-option-container"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="product-option-label">
                    옵션
                </label>
                <div className="flex gap-7 justify-start" id={"product-option-uses"}>
                    {uses.map((use, index) => (
                        <div id={"product-option-use-button-container" + index}>
                            <input type="radio" id={"option"+index} name="useGrop" value={use.value}
                                   className="mr-2 appearance-none w-4 h-4 rounded-full bg-gray-300 checked:bg-blue-500 checked:border-blue-500"
                                   onChange={handleUseChange}
                                   checked={use.checked}/>
                            <label htmlFor="use" className="text-gray-700">{use.label}</label>
                        </div>
                    ))}
                    {standardAble && <p>표준 가능</p>}
                </div>
                {optionUse && <div className="grid grid-row-3 gap-2">
                    {optionList?.map ((option, index) => (
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
            </div>
        </div>
    );
}

export default Option;