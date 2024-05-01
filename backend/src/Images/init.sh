
WORK_DIR=$(cd $(dirname $0) && pwd)
DATE=$(date +"%s")
cd "${WORK_DIR}"

if [ $# -eq 0 ]; then
	BRANCH="${DATE}_default"
	MSG="default"
else 
	BRANCH="$1"
	MSG="$2"
fi

#c
git branch -D "${BRANCH}"
git checkout -b "${BRANCH}"
rm -f "${WORK_DIR}/detail/template/template_01.jpg"
rm -f "${WORK_DIR}/detail/template/template_02.jpg"
rm -f "${WORK_DIR}/detail/template/template_04.jpg"
rm -f "${WORK_DIR}/detail/template/template_05.jpg"
rm -f "${WORK_DIR}/detail/template/template_06.jpg"
rm -f "${WORK_DIR}/detail/template/template_07.jpg"
rm -f "${WORK_DIR}/detail/template/template_08.jpg"
rm -f "${WORK_DIR}/detail/template/template_09.jpg"
rm -f "${WORK_DIR}/detail/template/template_10.jpg"
exit 0
