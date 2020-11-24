import React from 'react';
import { View } from 'react-native';
import { Button } from 'react-native-elements';
import Post from '../../components/PostComponent/Post';
import uniqid from 'uniqid';

const Feed = () => {
  const buttons = [<Button title='Prev' />, <Button title='Next' />];
  let posts = [
    {
      key: uniqid(),
      author: 'Kusti',
      text:
        'Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah',
    },
    {
      key: uniqid(),
      author: 'Kusti',
      text:
        'Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah',
    },
    {
      key: uniqid(),
      author: 'Kusti',
      text:
        'Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsumhallulahLorem ipsum hallulahLor',
    },
  ];

  //let total = posts.reduce(post, (acc = 0) => post.text.length + acc);
  //const reducer = (acc, cur) => acc + cur;
  let allPages = Math.ceil(
    posts.reduce((acc, post) => {
      return post.text.length + acc;
    }, 0) / 400
  );
  let currentPage = 0;

  return (
    <View
      style={{
        display: 'flex',
        flex: 1,
        flexWrap: 'wrap',
      }}>
      <Post author='Kusti'>
        Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum
        hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem
        ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah
      </Post>
      <Post author='Kusti'>
        Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum
        hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem
        ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah
      </Post>
      <Post author='Kusti'>
        Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum
        hallulahLorem ipsum hallulahLor
      </Post>
      <View
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center',
        }}>
        {/*****  SHOW BUTTONS IN CORRECT SITUATIONS  *******/}
        {allPages > 1 ? (
          currentPage === 0 ? (
            <View>{buttons[1]}</View>
          ) : (
            <View>
              {buttons.map((button) => (
                <View style={{ marginLeft: '5px', marginTop: '5px' }}>
                  {button}
                </View>
              ))}
            </View>
          )
        ) : null}
      </View>
    </View>
  );
};

export default Feed;
