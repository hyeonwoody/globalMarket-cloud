import React, {useEffect, useState} from 'react';
import {usePage} from "../../../store/PageContext";

interface HomeProps{
    onClickButton: (event: React.MouseEvent<HTMLButtonElement>) => void,
}



function Home (props:HomeProps) {

    const {pageState, clear, move, back, forward} =usePage();
    useEffect(() => {
        // Call the clear function when the component mounts
        clear();
        move("홈")
    }, []); // Empty dependency array to run the effect only once


    return (
        <div className="Container">



            <div className="p-4 sm:ml-64">
                <div className="p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">
                    <p>vcvcv</p>
                    <button
                        onClick={props.onClickButton}>
                        ffdfdf
                    </button>
                </div>
            </div>

        </div>

    )
        ;
}

export default Home;