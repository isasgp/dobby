import tensorflow as tf
from tensorflow.keras.preprocessing.text import Tokenizer
from keras_preprocessing.sequence import pad_sequences
import numpy as np
import json

with open("char_index", "r") as f:
    char_index = json.load(f)

tokenizer = Tokenizer(lower=True, char_level=True, oov_token='-n-')
tokenizer.word_index = char_index

# model = tf.keras.models.load_model("model_all.h5")
model = tf.keras.models.load_model('model_all.h5')

model.save('new_model')

def validate_malicious_domain(url):
    sequences = tokenizer.texts_to_sequences([url])
    x_test = pad_sequences(sequences, maxlen=200)

    predictions = model.predict(x_test)
    # print(predictions)
    predicted_class = np.argmax(predictions)  # 가장 높은 확률의 클래스 인덱스 반환

    return predicted_class

    # if predicted_class == 0:
    #     result = "legitimate"
    # elif predicted_class == 1:
    #     result = "phishing"

    # return {
    #     "url": url,
    #     "result": result,
    #     "probability": round(float(predictions[0][predicted_class]), 4)
    # }