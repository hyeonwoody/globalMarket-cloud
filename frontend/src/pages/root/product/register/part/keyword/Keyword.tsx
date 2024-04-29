import React, {ChangeEvent, useState} from "react";
import {RegisterState} from "../../ProductRegisterAPI";


interface KeywordProps {
    pageTitle : string,
    metaDescription : string,
    tagList : string[],
    callback : (field: keyof RegisterState, input : string, index? : number) => void
    deleteCallback : (index : number) => void
}




function Keyword (props : KeywordProps) {
    // const [pageTitle, setPageTitle] = useState<string>(props.pageTitle);
    // const [metaDescription, setMetaDescription] = useState<string>(props.metaDescription);
    // const [tagList, setTagList] = useState<string[]>(props.tagList);
    const [tag, setTag] = useState<string>("");


    const handleTagDelete = (event : React.MouseEvent<HTMLButtonElement>, index: number) =>{
        event.preventDefault();
        console.log("INDEX:"+index);
        props.deleteCallback(index);
    }

    const handlePasteChange = (field: keyof RegisterState, index : number) => (event: React.ClipboardEvent<HTMLInputElement>) => {
        switch (field) {
            case "pageTitle":
                //setPageTitle(event.clipboardData.getData('text'));
                break;
            case "metaDescription":
                //setMetaDescription(event.clipboardData.getData('text'));
                break;
            case "tagList":
                const updatedTag : string[] = props.tagList.map((tag, i) => {
                    return i === index ? event.clipboardData.getData('text') : tag;
                });
                //setTagList(updatedTag);
                break;
            default:
                break;
        }
    };
    const handleInputChange = (type : keyof KeywordProps, index? : number) =>
        (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
    ) => {
        switch (type){
            case "pageTitle":
                //setPageTitle(event.target.value);
                props.callback(type, event.target.value);
                break;
            case "metaDescription":
                //setMetaDescription(event.target.value);
                props.callback(type, event.target.value);
                break;
            case "tagList":
                const updatedKeywords : string[] = props.tagList.map((tag, i) => {
                    return i === index ? event.target.value : tag;
                });
                //setTagList(updatedKeywords);
                console.log("KEYWORD:BEFORE"+updatedKeywords);
                if (index != undefined)
                    props.callback(type, event.target.value, index);
                //console.log("KEYWORD:AFTER"+tagList);
                break;
        }
    }

    const handleInputTag = (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>) => {
        setTag(event.target.value);
    }

    const addTag = () => {
        console.log("SAVE");
        //console.log("saveBEFORE:"+tagList);
        if (props.tagList.length < 10){
            props.callback("tagList", tag, props.tagList.length);
        }
    };

    return (
        <div className="Keyword-Container flex flex-wrap -mx-3 mb-2" id={"product-keyword"}>
            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-title"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-name">
                    타이틀
                </label>
                <input
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                    id="grid-last-name"
                    type="text"
                    value={props.pageTitle}
                    placeholder={"타이틀"}
                    onChange={handleInputChange("pageTitle")}/>
            </div>
            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-metaDescription"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-name">
                    메타 설명
                </label>
                <input
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                    id="grid-last-name"
                    type="text"
                    value={props.metaDescription}
                    placeholder={"타이틀"}
                    onChange={handleInputChange("metaDescription")}/>
            </div>
            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-tags"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-name">
                    태그
                </label>
                <p>10개까지 입력해주세요</p>
                    <div className={"grid grid-cols-5 gap-2"}>
                    {props.tagList?.map((tag, index) => (
                        <div className="mb-2 md:mb-2" id={"product-tag"} key={index}>
                            <div className="flex items-center">
                                <input
                                    className="w-full appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                    id={index.toString()}
                                    type="text"
                                    value={props.tagList[index]}
                                    placeholder={tag}
                                    onChange={handleInputChange("tagList", index)}/>
                                <button
                                    className="bg-red-500 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={(event) => handleTagDelete(event, index)}
                                >
                                    X
                                </button>
                            </div>
                        </div>
                    ))}
                    </div>
                <input
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                    id="grid-last-name"
                    type="text"
                    value={tag}
                    placeholder={"태그 추가"}
                    onChange={handleInputTag}
                    onKeyDown={(event: React.KeyboardEvent<HTMLInputElement>) => {

                        if (event.code === "Enter" && props.tagList.length < 10) {
                            event.preventDefault();
                            event.stopPropagation();
                            // Add tag if the input value is not empty
                            console.log(event.target);
                            console.log("알아");
                            addTag();
                        }
                    }}/>
            </div>
        </div>
    )

}

export default Keyword;