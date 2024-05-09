import axios from "axios";
import React, {useLayoutEffect, useState} from "react";
import Dynamic from "../../component/Dynamic/Dynamic";

const Dynamicbody = (props) => {

    const [dynamicList, setDynamicList] = useState([]);

    useLayoutEffect(() => {
        axios.post('/api/getDynamic', {
            tag: props.tag,
            authorId: props.authorId,
            times: props.times,
            searchDate: props.searchDate,
        })
            .then(function (response) {
                console.log(response.data);
                setDynamicList(response.data.data);
            })
            .catch(function (error) {
                console.log(error);
            });


        if (typeof props.newDynamic != "undefined")
            setDynamicList([props.newDynamic, ...dynamicList]);
    }, [props])

    return (
        <div>
            {
                dynamicList != null ?
                    (dynamicList.filter((dynamic) => dynamic !== null).map((dynamic, index) => {
                        return (
                            <Dynamic key={index} {...dynamic} isLogin={props.isLogin}/>
                        )
                    })) : (<></>)
            }
        </div>
    );
}

export default Dynamicbody;