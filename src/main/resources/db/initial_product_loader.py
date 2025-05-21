# -*- coding: utf-8 -*-
import requests
import pymysql
import re
from dotenv import load_dotenv
from category_mapping import category_mapping
import os

print("스크립트 시작!")  # 확인용

# .env 파일 로드
load_dotenv(dotenv_path='.env')

# 환경변수로부터 값 불러오기
client_id = os.getenv('NAVER_CLIENT_ID')
client_secret = os.getenv('NAVER_CLIENT_SECRET')

headers = {
    "X-Naver-Client-Id": client_id,
    "X-Naver-Client-Secret": client_secret
}

keywords = [
    "패딩", "코트", "자켓", "점퍼", "블레이저",         # 아우터
    "셔츠", "블라우스", "티셔츠", "니트", "스웨터",     # 상의
    "청바지", "슬랙스", "반바지", "치마", "트레이닝팬츠", # 하의
    "원피스", "미디원피스", "롱원피스", "미니드레스",     # 드레스
    "운동화", "구두", "부츠", "로퍼", "샌들",           # 신발
    "백팩", "토트백", "크로스백", "클러치", "숄더백",     # 가방
    "캡모자", "버킷햇", "비니", "헌팅캡",               # 모자
    "귀걸이", "반지", "목걸이", "팔찌", "시계"           # 악세서리
]

try:
    connection = pymysql.connect(
        host=os.getenv("DB_HOST"),
        port=int(os.getenv("DB_PORT")),
        user=os.getenv("DB_USER"),
        password=os.getenv("DB_PASSWORD"),
        db=os.getenv("DB_NAME"),
        charset='utf8mb4'
    )
    print("DB 연결 성공!")
    cursor = connection.cursor()

    # ========== 수정 부분 START ==========
    # 기존 데이터 모두 삭제 (주의: 운영환경 X)
    cursor.execute("SET FOREIGN_KEY_CHECKS = 0")  # 외래키 체크 끄기 (필수)
    cursor.execute("TRUNCATE TABLE product")
    cursor.execute("TRUNCATE TABLE category")
    cursor.execute("SET FOREIGN_KEY_CHECKS = 1")  # 다시 켜기
    print("기존 데이터 초기화 완료!")
    # ========== 수정 부분 END ==========

    for keyword in keywords:
        print(f"키워드 검색 시작: {keyword}")
        url = f"https://openapi.naver.com/v1/search/shop.json?query={keyword}&display=10&start=1"
        response = requests.get(url, headers=headers)
        print(f"API 응답 코드: {response.status_code}")

        if response.status_code != 200:
            print(f"API 호출 실패: {response.text}")
            continue

        items = response.json().get('items', [])
        print(f"가져온 아이템 수: {len(items)}")

        for item in items:
            # 1. HTML 태그 제거
            clean_name = re.sub(r'<[^>]*>', '', item['title'])

            # 2. Gender 결정
            category2 = item.get('category2', '')
            if '남성' in category2:
                gender = '남성'
            elif '여성' in category2:
                gender = '여성'
            else:
                gender = '공용'

            # 3. Category3 (ex. 셔츠, 청바지 등) 처리
            original_category_name = item.get('category3', '미분류').strip()
            mapped_category_name = category_mapping.get(original_category_name, '미분류')

            print(f"카테고리 처리 중: {original_category_name} → {mapped_category_name}")

            # Category 존재 확인
            cursor.execute("SELECT id FROM category WHERE name = %s", (mapped_category_name,))
            result = cursor.fetchone()

            if result:
                category_id = result[0]
            else:
                cursor.execute("INSERT INTO category (name) VALUES (%s)", (mapped_category_name,))
                category_id = cursor.lastrowid
                print(f"새 카테고리 삽입: {mapped_category_name}")

            # 4. Product 중복 여부 확인
            cursor.execute("""
                SELECT id FROM product WHERE name = %s AND brand = %s AND price = %s AND category_id = %s AND gender = %s
            """, (clean_name, item['brand'], float(item['lprice']), category_id, gender))
            product_exists = cursor.fetchone()

            if product_exists:
                print(f"이미 존재하는 상품: {clean_name}")
                continue

            # 5. Product 삽입
            sql = """
                INSERT INTO product (name, brand, image_url, price, site_url, category_id, gender)
                VALUES (%s, %s, %s, %s, %s, %s, %s)
            """
            cursor.execute(sql, (clean_name, item['brand'], item['image'], float(item['lprice']),
                                 item['link'], category_id, gender))
            print(f"상품 삽입: {clean_name}")

    connection.commit()
    print("커밋 완료")
except Exception as e:
    print(f"오류 발생: {e}")
finally:
    connection.close()
    print("DB 연결 종료")