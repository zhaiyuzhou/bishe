import React, {useState} from 'react';
import {Button, Image, Input, Upload} from 'antd';
import './Pubdyn.css';
import {CustomerServiceTwoTone, PictureOutlined, PlaySquareOutlined, PlusOutlined} from '@ant-design/icons';

const { TextArea } = Input;

const getBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });

const Pubdyn = () => {

  // 上传的问字
  const [value, setValue] = useState('');

  // 改变视图
    const [isimgload, setIsimgload] = useState(false);

    const showImgload = () => {
        setIsimgload(!isimgload);
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
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        上传
      </div>
    </button>
  );

  console.log(fileList)

    //上传的视频
    const propsVideo = {
        action: '/updateVideo',
        listType: 'video',
        previewFile(file) {
            console.log('Your upload file:', file);
            // Your process logic. Here we just mock to the same file
            return fetch('http://localhost:3000/', {
                method: 'POST',
                body: file,
            })
                .then((res) => res.json())
                .then(({thumbnail}) => thumbnail);
        },
    };

    const propsMusic = {
        action: '/updateMusic',
        listType: 'music',
        previewFile(file) {
            console.log('Your upload file:', file);
            // Your process logic. Here we just mock to the same file
            return fetch('http://localhost:3000/', {
                method: 'POST',
                body: file,
            })
                .then((res) => res.json())
                .then(({thumbnail}) => thumbnail);
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
        <Upload className='Pubdyn-upload-video' {...propsVideo}>
            <Button type="text" icon={<PlaySquareOutlined/>}></Button>
        </Upload>
        <Upload className='Pubdyn-upload-music' {...propsMusic}>
            <Button type="text" icon={<CustomerServiceTwoTone twoToneColor="black"/>}></Button>
        </Upload>
        <div className='Pubdyn-upload-img' style={{display: (isimgload ? 'block' : 'none')}}>
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
        <Button className='Pubdyn-input' type="primary">发布</Button>
    </div>
  )
};

export default Pubdyn;