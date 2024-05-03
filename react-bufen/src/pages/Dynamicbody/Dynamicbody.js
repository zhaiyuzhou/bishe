import axios from "axios";
import React, {useLayoutEffect, useState} from "react";
import Dynamic from "../../component/Dynamic/Dynamic";

const Dynamicbody = (props) => {

    const [dynamicList, setDynamicList] = useState([]);

    useLayoutEffect(() => {
            axios.post('/getDynamic', {
                tag: props.tag,
                authorId: props.authorId,
            })
                .then(function (response) {
                    console.log(response.data);
                    setDynamicList(response.data.data);
                })
                .catch(function (error) {
                    console.log(error);
                });
    }, [props.tag, props.authorId])

    return (
        <div>
            {
                dynamicList != null ?
                    (dynamicList.filter((dynamic) => dynamic !== null).map((dynamic, index) => {
                    return (
                        <Dynamic key={index} {...dynamic} {...dynamic.author} />
                    )
                    })) : (<></>)
            }
        </div>
    );
}

export default Dynamicbody;