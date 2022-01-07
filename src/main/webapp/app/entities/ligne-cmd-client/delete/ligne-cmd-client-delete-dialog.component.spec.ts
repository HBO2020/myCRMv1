jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { LigneCmdClientService } from '../service/ligne-cmd-client.service';

import { LigneCmdClientDeleteDialogComponent } from './ligne-cmd-client-delete-dialog.component';

describe('LigneCmdClient Management Delete Component', () => {
  let comp: LigneCmdClientDeleteDialogComponent;
  let fixture: ComponentFixture<LigneCmdClientDeleteDialogComponent>;
  let service: LigneCmdClientService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LigneCmdClientDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(LigneCmdClientDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LigneCmdClientDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneCmdClientService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
