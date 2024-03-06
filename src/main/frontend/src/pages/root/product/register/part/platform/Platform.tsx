import React, {useState} from "react";
import {RegisterState} from "../../ProductRegisterAPI";
import {platformList} from "../../../../../../configuration/platform";

interface PlatformProps {
    callback : (index : number) => void
}

function Platform (props : PlatformProps) {
    const [dropdown, setDropdown] = useState (false);

    const [platformState, setPlatform] = useState ("네이버");
    const toggleDropdown = () => {
        setDropdown(!dropdown);
    }

    const handleOption = (result : number) => {
        setPlatform(platformList[result])
        toggleDropdown();
        props.callback(result);
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
    return (
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
    )
}

export default Platform;