import React, {useEffect, useState} from 'react';
import RootAxios from "./RootAPI"

import {useAuth} from "../../store/AuthContext";
import Auth from "./part/Auth"

import NavBar_SearchBar from "./part/NavBar_SearchBar"
import NavBar_User from "./part/NavBar_User";

import BreadCrumb from "./part/BreadCrumb";

import {usePage} from "../../store/PageContext";
import Home from "./home/Home"
import ProductRegister from "./product/register/ProductRegister"


interface MenuItem {
    label: string;
    submenu?: MenuItem[];
}

const menuItems: MenuItem[] = [
    {
        label: "상품",
        submenu: [
            { label: "등록" },
            { label: "수정" },
            { label: "삭제" }
        ],
    },
    {
        label: "주문",
        submenu: [
            { label: "조회" },
            { label: "교환" },
            { label: "반품" }
        ],
    },
    {
        label: "문의",
        submenu: [
            { label: "조회" },
            { label: "교환" },
            { label: "반품" }
        ],
    }
];
const Root: React.FC = () => {
    const { authState, login, logout } = useAuth();
    const [pageIndex, setPage] = useState({ index: -1, subIndex: -1});
    const [isDropdownOpen, setIsDropdownOpen] = useState(-1);
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const {pageState, clear, move, back, forward} =usePage();
    const onClickButton = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        console.log("cvcv")
        function resultCallback(result: number) {
            console.log("result" + result);

            if (result < 0) {
                //error handling
            }
        }

        RootAxios(resultCallback);
    }

    const onClickLogin = (username : string) => {
        login(username);
    }

    const toggleDropdown = (index : number) =>{
        if (index == isDropdownOpen)
            setIsDropdownOpen(-1)
        else {
            setIsDropdownOpen(index);
        }

        if (pageState.page[pageState.page.length-1] == "홈"){
            forward (menuItems[index].label)
        }
        else {
            back()
            forward(menuItems[index].label);
        }
    }

    const onClickSubMenu = (index: number, subIndex: number) =>{
        setPage({ ...pageIndex, index: index, subIndex: subIndex });
        console.log("SUBmen")
        if (index !== -1 && subIndex !== -1 && menuItems[index]?.submenu?.[subIndex]) {
            console.log ("PAGE LEVEL "+ pageState.level)
            clear()

            forward("홈")
            // @ts-ignore
            forward (menuItems[index].label)
            // @ts-ignore
            forward (menuItems[index].submenu[subIndex].label)

            // @ts-ignore
            console.log(menuItems[index].label + menuItems[index].submenu[subIndex]?.label);
            console.log(pageIndex.index + pageIndex.subIndex);
        }
    }


    return (
        <div className="Container">
            <nav className="bg-green-500 border-gray-200 dark:bg-gray-900 dark:border-gray-700">
                <div className="flex flex-wrap items-center justify-between mx-auto p-4">
                    <button className="flex items-center space-x-3 rtl:space-x-reverse" onClick={ ()=>onClickSubMenu(-1,-1)}>
                        <span
                            className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white" >세계장터</span>
                    </button>
                    <NavBar_SearchBar/>
                    <NavBar_User/>
                </div>
            </nav>

            <BreadCrumb />
            <div>

                {authState.isLoggedIn ?

                    <>
                        <button
                            className="fixed bottom-4 left-4 z-50 flex text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                            onClick={() => setIsSidebarOpen(!isSidebarOpen)}>
                            <svg className="w-4 h-4 text-gray-800 dark:text-white" aria-hidden="true"
                                 xmlns="http://www.w3.org/2000/svg"
                                 fill="none" viewBox="0 0 24 24">
                                <path stroke="black" d="M5 7h14M5 12h14M5 17h14"/>
                            </svg>
                        </button>
                        <aside id="sidebar-multi-level-sidebar"
                            //className="fixed top-30 left-0 z-40 w-full h-screen bg-white border-gray-200 dark:bg-gray-900 dark:border-gray-700 sm:w-64 sm:translate-x-0"
                            // Conditional classes for responsive design
                               className={`${isSidebarOpen ? 'sm:translate-x-0' : '-translate-x-full sm:translate-x-0'} fixed top-30 left-0 z-40 w-64 h-screen bg-white border-gray-200 dark:bg-gray-900 dark:border-gray-700`}
                        >

                            <div className="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-700">
                                <ul className="space-y-2 font-medium">
                                    {menuItems.map((menuItem, index) => (
                                        <li key={index}>
                                            <button type="button"
                                                    className="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                                    aria-controls={`dropdown-example-${index}`}
                                                    onClick={() => toggleDropdown(index)}>
                                                <span
                                                    className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">{menuItem.label}</span>
                                                <svg className="w-3 h-3" aria-hidden="true"
                                                     xmlns="http://www.w3.org/2000/svg"
                                                     fill={isDropdownOpen === index ? 'white' : "none"}
                                                     viewBox="0 0 10 6">
                                                    <path stroke="currentColor" d="m1 1 4 4 4-4"/>
                                                </svg>
                                            </button>
                                            <ul id={`dropdown-example-${index}`}
                                                className={`py-2 space-y-2 ${isDropdownOpen === index ? '' : 'hidden'}`}>
                                                {menuItem.submenu?.map((subitem, subindex) => (
                                                    <li key={subindex}>
                                                        <button
                                                            className="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                                            onClick={() => onClickSubMenu(index, subindex)}> {subitem.label}
                                                        </button>
                                                    </li>
                                                ))}
                                            </ul>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </aside>
                        { (pageIndex.index == -1 && pageIndex.subIndex == -1) ? <Home onClickButton={onClickButton}/> :
                            (pageIndex.index == 0 && pageIndex.subIndex == 0) ? <ProductRegister /> : null
                        }
                    </>

            :
            <Auth authLogin={onClickLogin} authLogout={logout}/>
            }
        </div>


</div>
)
    ;
};

export default Root;