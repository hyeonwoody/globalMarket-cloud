import React, {ChangeEvent, useEffect, useState} from "react";
import {ProductOptionStandard} from "./Option";

interface OptionStandardProps {
    fetchData : boolean
    standardOption : ProductOptionStandard;
    callback : (value : ProductOptionStandard) => void
}

function OptionStandard (props :OptionStandardProps) {
    const [attributesUsed, setAttributesUsed] =
        useState<boolean[][]>(()=>{
            let tmpAttributes : boolean[][] = [];
            for (let i = 0; i < props.standardOption.standardOptionCategoryGroups.length; i++) {
                tmpAttributes.push(new Array(props.standardOption.standardOptionCategoryGroups[i].standardOptionAttributes.length).fill(false)); // Add inner array with null values
            }
            return (tmpAttributes);
        });
    useEffect(() => {
        fetchData();
    }, [props.fetchData]);

    const fetchData = async () => {
        if (props.fetchData){
            console.log("표준옵션zzzz");
            // const filteredAttributes = {
            //     ...props.standardOption,
            //     standardOptionCategoryGroups :
            //     props.standardOption.standardOptionCategoryGroups.map((group, i) => (
            //         { ...group, standardOptionAttributes: group.standardOptionAttributes.filter((_, index) => attributesUsed[i][index]) }
            //     ))
            // }
            // console.log(filteredAttributes);
            // props.callback(filteredAttributes);
            let iIndex = 0;
            const filter = {
                ...props.standardOption,
                standardOptionCategoryGroups: props.standardOption.standardOptionCategoryGroups.filter((group, i) => {
                    group.standardOptionAttributes = group.standardOptionAttributes.map((attribute, j) => ({
                        ...attribute,
                        usable: attributesUsed[i][j]
                    }));
                    return group.standardOptionAttributes.some((attribute) => {
                        return (attribute.usable);
                    });
                })
                    .map(group => ({
                    ...group,
                    standardOptionAttributes: group.standardOptionAttributes.filter((attribute) => attribute.usable)
                    }))
            };
            console.log(iIndex);
            console.log(filter);
            props.callback(filter);
        }
    };

    const handleInputStandardOptionChange = (value : string, index : number) =>
        (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
        ) => {

    }
    const onClickAttributes = ( event : React.MouseEvent<HTMLButtonElement>, optionIndex : number, attributeIndex : number) => {
        event.preventDefault();
        console.log ( "attribute : ", optionIndex, attributeIndex)
        setAttributesUsed((prevState) => {
            const newAttributes = [...prevState];
            newAttributes[optionIndex][attributeIndex] = !prevState[optionIndex][attributeIndex];
            return newAttributes;
        });
    }

    interface ButtonStyles {
        border : string
    }

    const AttributesButtonStyles : (optionIndex : number, attributeIndex : number) => ButtonStyles = (optionIndex, attributeIndex) =>
    {

        if (attributesUsed.length > optionIndex && attributesUsed[optionIndex].length > attributeIndex) {
            const isAttributeUsed = attributesUsed[optionIndex][attributeIndex];
            return {
                border: `2px solid ${isAttributeUsed ? 'red' : 'grey'}`,
            };
        } else {
            // Handle the case where attributesUse isn't populated yet
            return { border: 'none' }; // Or a default style
        }
    };

    const AttriubtesContainer = () => {
        return {
            display : "grid",
        }
    };

    return (
        <div className="grid grid-row-3 gap-2">
            {props.standardOption.standardOptionCategoryGroups.map((group, optionIndex) => (
                <div className={"flex justify-start gap-2"}>
                    <input
                        className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                        id="grid-last-name"
                        type="text"
                        value={group.attributeName}
                        placeholder={group.attributeName}
                        onChange={handleInputStandardOptionChange("name", optionIndex)}/>
                    {group.standardOptionAttributes.map ((attribute, attributeIndex) => (
                        <div className={"attrbutes"} style={AttriubtesContainer()}>
                            <button className={`attributes button`}
                                    style={AttributesButtonStyles(optionIndex, attributeIndex)}
                                    onClick={(event) => onClickAttributes(event, optionIndex, attributeIndex)}> {attribute.attributeValueName}
                            </button>
                        </div>
                    ))}
                    <div className="flex justify-center border-2 border-amber-300">
                        <button>삭제</button>
                        <button>추가</button>
                    </div>
                </div>
            ))}
        </div>
    )
}

export default OptionStandard;