import os
import google.auth
from googleapiclient.discovery import build
from googleapiclient.http import MediaFileUpload
from google.oauth2.credentials import Credentials
from google_auth_oauthlib.flow import InstalledAppFlow

SCOPES = ['https://www.googleapis.com/auth/drive.file']

def get_refresh_token(client_secrets_file):
    flow = InstalledAppFlow.from_client_secrets_file(client_secrets_file, SCOPES)
    creds = flow.run_local_server(port=0)

    with open('token.json', 'w') as token:
        token.write(creds.to_json())

    return creds.refresh_token

def upload_to_drive(file_path, refresh_token):
    try:
        creds = Credentials(
            token=None,
            refresh_token=refresh_token,
            token_uri='https://oauth2.googleapis.com/token',
            client_id=os.environ['CLIENT_ID'],
            client_secret=os.environ['CLIENT_SECRET'],
            scopes=SCOPES,
        )

        service = build('drive', 'v3', credentials=creds)

        file_metadata = {'name': os.path.basename(file_path)}
        media = MediaFileUpload(file_path, mimetype='application/zip')

        file = service.files().create(body=file_metadata, media_body=media, fields='id').execute()
        print(f'Upload realizado com sucesso! File ID: {file.get("id")}')
    
    except Exception as e:
        print(f'Erro durante o upload: {e}')

if __name__ == "__main__":
    refresh_token = None
    if os.path.exists('token.json'):
        with open('token.json', 'r') as token:
            creds = Credentials.from_authorized_user_file('token.json', SCOPES)
            refresh_token = creds.refresh_token
    else:
        refresh_token = get_refresh_token('client_secret.json')

    file_path = 'arquivo.zip'

    if os.path.exists(file_path):
        upload_to_drive(file_path, refresh_token)
    else:
        print(f'Arquivo {file_path} não encontrado.')
