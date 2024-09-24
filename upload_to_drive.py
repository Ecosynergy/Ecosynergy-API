import os
import google.auth
from googleapiclient.discovery import build
from googleapiclient.http import MediaFileUpload
from google.oauth2.credentials import Credentials

def upload_to_drive(file_path):
    try:
        creds = Credentials(
            token=None,
            refresh_token=os.environ['REFRESH_TOKEN'],
            token_uri='https://oauth2.googleapis.com/token',
            client_id=os.environ['CLIENT_ID'],
            client_secret=os.environ['CLIENT_SECRET'],
            scopes=['https://www.googleapis.com/auth/drive.file'],
        )

        service = build('drive', 'v3', credentials=creds)

        file_metadata = {'name': os.path.basename(file_path)}
        media = MediaFileUpload(file_path, mimetype='application/zip')

        file = service.files().create(body=file_metadata, media_body=media, fields='id').execute()
        print(f'Upload realizado com sucesso! File ID: {file.get("id")}')
    
    except Exception as e:
        print(f'Erro durante o upload: {e}')

if __name__ == "__main__":
    file_path = 'arquivo.zip'

    if os.path.exists(file_path):
        upload_to_drive(file_path)
    else:
        print(f'Arquivo {file_path} não encontrado!')
