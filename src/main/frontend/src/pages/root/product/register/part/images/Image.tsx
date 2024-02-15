import React, {useState} from 'react';
import {ProductImage} from "../../../ProductAPI";
import Category from "../Category";

interface ImageProps {
    images : ProductImage
    callback : (index : number) => void
}

function Image (props : ImageProps) {

    const onClickDelete = (event : React.MouseEvent<HTMLButtonElement>, index : number) => {
        event.preventDefault();

        console.log ("INDEX : "+ index)
        props.callback(index);
    }


    return (
        <div className="Image-Container flex flex-wrap -mx-3 mb-2" id={"product-images"}>
            <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-representative"}>
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-name">
                    대표 이미지
                </label>

                    <img key={"image0"} className={"rounded-full max-w-full max-h-full"} src={props.images.representativeImage.url}/>
                    <button
                        key={"button0"}
                    className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block"
                    onClick ={(event)=>onClickDelete(event, 0)}
            >
                삭제
            </button>



                {props.images.optionalImages.length > 0? props.images.optionalImages.map((value, index) => (
                    <div className={"product-optional-image"}>
                        <img key={"image"+index+1} className={"rounded-full max-w-full max-h-full"} src={value.url} />
                        <button
                            key={"button"+index+1}
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block"
                            onClick ={(event)=>onClickDelete(event, index+1)}
                            >
                            삭제
                        </button>
                    </div>

                )) : null}

            </div>
        </div>
    )

}

export default Image;
