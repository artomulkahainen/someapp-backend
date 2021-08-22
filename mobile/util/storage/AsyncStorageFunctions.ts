interface AsyncStorageProps {
    getItem: (callback?: ((error?: Error | undefined, result?: string | undefined) => void) | undefined) => Promise<string | null>;
    setItem: (value: string, callback?: ((error?: Error | undefined) => void) | undefined) => Promise<void>;
    removeItem: (callback?: ((error?: Error | undefined) => void) | undefined) => Promise<void>;
}

const AsyncStorageFunctions = ({ getItem, setItem, removeItem }: AsyncStorageProps) => {
    const getToken = async (): Promise<string | null> => {
        try {
          return await getItem();
        } catch (e: any) {
          throw new Error(e.message);
        }
    };

    const saveToken = async (token: string): Promise<void> => {
        try {
          await setItem(token);
        } catch (e: any) {
          throw new Error(e.message);
        }
    };

    const removeToken = async (): Promise<void> => {
        try {
          await removeItem();
        } catch (e: any) {
          throw new Error(e.message);
        }
    };

    return {
        getToken,
        saveToken,
        removeToken
    };
};

export default AsyncStorageFunctions;