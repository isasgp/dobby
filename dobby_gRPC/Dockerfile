FROM python:3.9-slim

WORKDIR /app
COPY requirements.txt /app/requirements.txt

# RUN pip install --no-cache-dir --timeout=120 -r requirements.txt
RUN pip install -r requirements.txt

COPY . /app
CMD ["python", "server.py"]