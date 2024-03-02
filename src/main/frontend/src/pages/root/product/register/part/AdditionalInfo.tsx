import React, {ChangeEvent, useEffect, useState} from 'react';
import AdditionalInfoAPI, {AdditionalInfoAxios} from "./AdditionalInfoAPI";

interface AdditionalInfoProps{
    fetchData : boolean,
    platform : number,
    category :string[],
    callback : (data : string[]) => void,
}
function AdditionalInfo(props:AdditionalInfoProps) {
    const [additionalInfoList, setAdditionalInfoList] = useState<string[]>([]);
    const [title, setTitle] = useState<string[]>([]);

    useEffect(() => {
        console.log("BEFORE")
        console.log(additionalInfoList);
        AdditionalInfoAxios(AxiosCallback, props);
        console.log("AFTER")
        console.log(additionalInfoList);
    }, [props.category]);

    if (props.fetchData) {
        props.callback(additionalInfoList);
    }

    const AxiosCallback = (data : string[]) => {
        console.log(data);
        setAdditionalInfoList(data);
        setTitle(data);
    }

    const handleInputChange = (index : number) =>
        (event: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>) =>
    {
        console.log(event.target.value);
        //I Still Dont get why it doesnt work
        // setAdditionalInfoList((prevState : string[]) => {
        //     let updatedAdditionalInfoList = prevState;
        //     updatedAdditionalInfoList[index] = event.target.value;
        //     return updatedAdditionalInfoList;
        // });

        setAdditionalInfoList((prevState) => {
            // Create a copy of the original array
            const updatedAdditionalInfoList = [...prevState];

            // Update the element at the specific index
            updatedAdditionalInfoList[index] = event.target.value;

            // Return the updated array
            return updatedAdditionalInfoList;
        });
    }

    return (
        <div className="flex flex-wrap -mx-3 mb-2" id={"product-additionalInfo"}>
            {additionalInfoList?.map((info, index) => (
                <div className="md:w-1/2 px-3 mb-0 md:mb-2" id={"product-price"} key={index}>
                    <label
                        className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                        htmlFor="grid-product-name">
                        {title[index]}
                    </label>
                    <input
                        className="appearance-none block bg-gray-200 text-gray-700 border border-gray-200 rounded py-2 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                        id="grid-last-name"
                        type="text"
                        value={info}
                        placeholder={info}
                        onChange={handleInputChange(index)}/>
                </div>
            ))}

        </div>
    )
}

export default AdditionalInfo;