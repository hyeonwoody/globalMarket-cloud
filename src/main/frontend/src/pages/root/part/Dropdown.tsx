import React, {useState} from 'react';

interface DropdownProps {
    element : string[]
    callback: (result : number) => void,
}

export function Dropdown (props : DropdownProps) {
    const [isOpen, setIsOpen] = useState (false);
    const [result, setResult] = useState (props.element[0]);

    const toggleDropdown = () => {

        setIsOpen(!isOpen);
        console.log("토글"+ isOpen)
    }

    return (
        <div className="Container">
            <button id="dropdownHoverButton" data-dropdown-toggle="dropdownHover" data-dropdown-trigger="hover"
                    onClick={toggleDropdown}
                    className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    type="button">{result} <svg className="w-2.5 h-2.5 ms-3" aria-hidden="true"
                                                      xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                <path stroke="currentColor"
                      d="m1 1 4 4 4-4"/>
            </svg>
            </button>

            {isOpen && (
                <div id="dropdownHover"
                     className="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                    <ul className="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownHoverButton">
                        {props.element.slice(1).map((item, index) => (
                            <button
                                key={index}
                                onClick={() => props.callback(index - 1)}
                                className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                            >
                                {item[index]}
                            </button>
                        ))}
                    </ul>
                </div>
            )}

        </div>
    );
}

export default Dropdown;