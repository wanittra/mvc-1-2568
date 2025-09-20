# mvc-1-2568 ข้อที่ 2 
โครงสร้างไฟล์ของโปรเจค:

App.java → Main class ของโปรแกรม รันโปรแกรมและเรียก View เริ่มต้น (LoginView)

data/<br>
    students.csv → เก็บข้อมูลนักเรียน<br>
    subjects.csv → เก็บข้อมูลรายวิชา<br>
    registrations.csv → เก็บข้อมูลการลงทะเบียนวิชาและเกรด<br>
    users.csv → เก็บข้อมูลผู้ใช้สำหรับ login (นักเรียน/แอดมิน)<br>

src/<br>
    App.java → Main class สำหรับรันโปรแกรม และเรียก View เริ่มต้น (Login)

controller/<br>
    MainController.java → ทำหน้าที่เป็นตัวกลางของระบบ (Controller)<br>
    - ตรวจสอบ Business Rule ก่อนลงทะเบียน เช่น อายุ ≥ 15 ปี, วิชาบังคับ, จำนวนคนสูงสุด<br>
    - เรียกใช้ Model เพื่อดึงข้อมูลนักเรียน, วิชา, การลงทะเบียน<br>
    - คืนผลลัพธ์กลับไปให้ View แสดงข้อความหรือ GUI<br>

model/<br>
    Student.java → เก็บข้อมูลนักเรียนและเมธอดช่วยเหลือ เช่น getFullName(), getAge()<br>
    Subject.java → เก็บข้อมูลรายวิชาและเมธอดช่วยเหลือ เช่น hasCapacity(), incrementRegistered()<br>
    Registration.java → เก็บข้อมูลการลงทะเบียนของนักเรียนในแต่ละวิชา<br>
    StudentModel.java → อ่าน/เขียน students.csv และจัดการข้อมูลนักเรียน เช่น getAll(), getById()<br>
    SubjectModel.java → อ่าน/เขียน subjects.csv และจัดการข้อมูลรายวิชา เช่น getAll(), getById(), save()<br>
    RegistrationModel.java → อ่าน/เขียน registrations.csv และจัดการข้อมูลการลงทะเบียน เช่น getByStudent(), addRegistration(), countRegistered(), isRegistered()<br>
    AuthModel.java → อ่าน/เขียน users.csv สำหรับระบบ login<br>

view/<br>
    LoginView.java → หน้าจอ login สำหรับนักเรียนและแอดมิน<br>
    StudentProfileView.java → แสดงข้อมูลนักเรียนและประวัติการลงทะเบียน<br>
    RegistrationView.java → หน้าลงทะเบียนวิชา และเลือกวิชาที่ยังไม่ได้ลงทะเบียน<br>
    SubjectDetailView.java → แสดงรายละเอียดของรายวิชา (จำนวนคนสูงสุด, คนลงทะเบียนปัจจุบัน, prereq)<br>
    AdminView.java → หน้าแอดมินสำหรับกรอกเกรดและจัดการรายวิชา<br>

การทำงานร่วมกัน

1.ผู้ใช้เปิดโปรแกรม → App.java เรียก LoginView

2.เมื่อ login สำเร็จ → Controller (MainController) จะดึงข้อมูลจาก Model (StudentModel, SubjectModel, RegistrationModel)

3.Controller ตรวจสอบ Business Rule ก่อนอนุญาตให้ลงทะเบียน

4.View แสดงข้อมูลที่ Controller ส่งกลับ เช่น รายวิชาที่ลงทะเบียนได้, ข้อความแจ้งเตือน (อายุ < 15, prereq ไม่ผ่าน, วิชาเต็ม)

5.เมื่อผู้ใช้ทำ action เช่น ลงทะเบียน → Controller จะอัปเดต Model → Model บันทึกข้อมูลลง CSV → View อัปเดตข้อมูลล่าสุด

# test case<br>
▪ จำนวนคนที่ลงทะเบียนในแต่ละวิชา ≥ 0 

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/2bf51eef-6410-40a0-bc54-003932d10e49" />

▪ นักเรียนต้องมีอายุอย่างน้อย 15 ปี 

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/94ad1f6e-583d-4c61-9bd8-a16ad0e9bcb4" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/88a991d5-25ca-4bea-8c4d-8ca4175dc4f6" />

▪ เมื่อลงทะเบียนสำเร็จ ต้องกลับไปหน้าประวัตินักเรียน 

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/0cb1f70e-91d2-4fd3-9117-e3b9ff88965a" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/6cf33908-4235-4a0d-a355-6395dc6acef3" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/570d36ab-2476-4a2e-96b2-41e66f142e56" />

▪ ตรวจสอบวิชาบังคับก่อน หากนักเรียนยังไม่ได้เกรดวิชา จะไม่สามารถลงวิชาต่อไปได้ 

1.กรณียังไม่ได้ลงวิชาบังคับ/ไม่ได้เกรดวิชา

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/5039c436-b04c-4cfe-8538-b11f2a50bff1" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/1b311cd6-ad1f-4924-bbbd-a3e28ee58846" />

2.กรณีลงแล้ว/ได้เกรดเรียบร้อย

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/ed6fee2f-e8e2-4ff8-b910-8c3e0e647441" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/5da6017b-d4a4-4f1f-bb02-73aa9c3dfd99" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/ca44e84a-1544-4aa9-b5bc-80a8805fe741" />


▪ หากผู้ที่ลงทะเบียนแล้ว มากกว่าจำนวนสูงสุด จะไม่สามารถลงทะเบียนได้ 

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/0af34059-d4e5-44f7-b354-cac518b70d53" />

▪Admin กรอกเกรดให้กับนักเรียนที่ลงทะเบียนเรียนในวิชานั้นๆ เรียบร้อยแล้ว

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/5229fd6f-7334-436a-a860-23c81e2b0c18" />
<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/4de38d18-458f-498b-9406-508f5ca1dce2" />

▪ หน้ารายละเอียดวิชา: กดปุ่ม "Details" จะแสดงรายละเอียดวิชา

<img width="400" height="200" alt="image" src="https://github.com/user-attachments/assets/941dcceb-3496-4665-94a2-3bf33a16019b" />

