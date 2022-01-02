import React, { useCallback, useState } from 'react';
import { useFocusEffect } from '@react-navigation/native';
import PostListComponent from '../../components/PostListComponent/PostListComponent';
import { findMyUserDetails } from '../../services/userService';
import { ActivityIndicator, StyleSheet, View } from 'react-native';
import { getFriendsPosts } from '../../services/postService';
import { red } from '../../util/styles/Colors';

const FeedView = () => {
    const [loading, setLoading] = useState<boolean>(false);

    useFocusEffect(
        useCallback(() => {
            setLoading(true);
            Promise.all([findMyUserDetails(), getFriendsPosts()]).finally(() => setLoading(false));
        }, [])
    );

    return (
        <View style={styles.container}>
            {loading ? <ActivityIndicator size={100} color={red} /> : <PostListComponent />}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center'
    }
});

export default FeedView;
