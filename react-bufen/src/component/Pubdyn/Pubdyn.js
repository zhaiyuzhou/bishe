import React, {useState} from 'react';
import {Button, Image, Input, Upload} from 'antd';
import './Pubdyn.css';
import {CloudUploadOutlined, CustomerServiceTwoTone, PictureOutlined, PlaySquareOutlined} from '@ant-design/icons';
import axios from 'axios';

const { TextArea } = Input;

const getBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });

const Pubdyn = () => {

    // 上传文字
  const [value, setValue] = useState('');

    const propsContent = () => {
        axios.post('/dynamicText', {
            content: value,
        })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

  // 改变视图
    const [isimgload, setIsimgload] = useState(false);
    const [isvideoload, setIsvideoload] = useState(false);
    const [ismusicload, setIsmusicload] = useState(false);

    const showImgload = () => {
        setIsimgload(!isimgload);
    }

    const showvideoload = () => {
        setIsvideoload(!isvideoload);
    }

    const showmusicload = () => {
        setIsmusicload(!ismusicload);
    }

  // 上传的图片
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [fileList, setFileList] = useState([]);
  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    setPreviewImage(file.url || file.preview);
    setPreviewOpen(true);
  };
  const handleChange = ({ fileList: newFileList }) => setFileList(newFileList);
  const uploadButton = (
    <button
      style={{
        border: 0,
        background: 'none',
      }}
      type="button"
    >
        <CloudUploadOutlined/>
      <div
        style={{
          marginTop: 8,
        }}
      >
          图片
      </div>
    </button>
  );

    //上传视频
    const propsVideo = {
        action: '/updateVideo',
        listType: 'video',
        previewFile(file) {
            if (file.type === 'video/mp4') {
                return new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onload = () => resolve(reader.result);
                    reader.onerror = (error) => reject(error);
                });
            } else {
                return new Promise(async (resolve) => {
                    const img = await getBase64(file);
                    resolve(img);
                });
            }
        },
    };

    // 上传音乐
    const propsMusic = {
        action: '/updateMusic',
        listType: 'music',
        previewFile(file) {
            if (file.type === 'video/mp4') {
                return new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onload = () => resolve(reader.result);
                    reader.onerror = (error) => reject(error);
                });
            } else {
                return new Promise(async (resolve) => {
                    const img = await getBase64(file);
                    resolve(img);
                });
            }
        },
    };

  return (
    <div className='Pubdyn-div'>
      <TextArea
        value={value}
        onChange={(e) => setValue(e.target.value)}
        placeholder="想有什么于大家分享"
        autoSize={{
          minRows: 3,
          maxRows: 5,
        }}
      />
        <Button type="text" icon={<PictureOutlined/>} onClick={showImgload}/>
        <Button type="text" icon={<PlaySquareOutlined/>} onClick={showvideoload}/>
        <Button type="text" icon={<CustomerServiceTwoTone twoToneColor="black"/>} onClick={showmusicload}/>
        <Button className='Pubdyn-input' type="primary" onClick={propsContent}>发布</Button>
        <div className='Pubdyn-upload'>
            <div className='Pubdyn-upload-img' style={{display: (isimgload ? 'inline-block' : 'none')}}>
                <Upload
            action="/updateImg"
            listType="picture"
            fileList={fileList}
            onPreview={handlePreview}
            onChange={handleChange}
                >
                    {fileList.length >= 8 ? null : uploadButton}
                </Upload>
                {previewImage && (
                    <Image
                        wrapperStyle={{
                            display: 'none',
                        }}
                        preview={{
                            visible: previewOpen,
                            onVisibleChange: (visible) => setPreviewOpen(visible),
                            afterOpenChange: (visible) => !visible && setPreviewImage(''),
                        }}
                        src={previewImage}
                    />
                )}
            </div>
            <div className='Pubdyn-upload-video' style={{display: (isvideoload ? 'inline-block' : 'none')}}>
                <Upload {...propsVideo}>
                    <button
                        style={{
                            border: 0,
                            background: 'none',
                        }}
                        type="button"
                    >
                        <CloudUploadOutlined/>
                        <div
                            style={{
                                marginTop: 8,
                            }}
                        >
                            视频
                        </div>
                    </button>
                </Upload>
            </div>
            <div className='Pubdyn-upload-music' style={{display: (ismusicload ? 'inline-block' : 'none')}}>
                <Upload {...propsMusic}>
                    <button
                        style={{
                            border: 0,
                            background: 'none',
                        }}
                        type="button"
                    >
                        <CloudUploadOutlined/>
                        <div
                            style={{
                                marginTop: 8,
                            }}
                        >
                            音乐
                        </div>
                    </button>
                </Upload>
            </div>
        </div>
    </div>
  )
};

export default Pubdyn;