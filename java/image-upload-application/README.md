# 이미지 업로드 어플리케이션

## 목표

각 개발 환경별로 적절한 이미지 저장 방식을 취할 수 있도록 여러가지 구현체를 제공한다.

- `AmazonS3ImageDownloader`, `AmazonS3ImageUploader`: Amazon S3 스토리지에 이미지 업로드 및 다운로드
- `FileStorageImageDownloader`, `FileStorageImageUploader`: 로컬에 이미지 업로드 및 다운로드
- `InMemoryImageDownloader`, `InMemoryImageUploader`: 메모리에 이미지 업로드 및 다운로드

이미지를 S3 스토리지에 저장하는 경우, 요청을 통해 받은 MultipartFile을 임시로 로컬에 저장하지 않는다. `InputStream`을 바로 S3 업로드시 전송할 수 있다.

S3 관련 의존성만 사용하기 위해 spring cloud aws를 사용하지 않는다. spring cloud aws를 사용할 때 EC2와 관련된 설정이 없어 경고 문구가 발생하는 번거로움을 줄이기 위함이다. 또한,
최소한의 의존성만 가지도록 하기 위한 조치다.

## API

- POST /images/upload: 이미지 업로드
- GET /images/:filename: 이미지 다운로드
