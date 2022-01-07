import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';

import { LigneCmdFournisseurComponent } from './ligne-cmd-fournisseur.component';

describe('LigneCmdFournisseur Management Component', () => {
  let comp: LigneCmdFournisseurComponent;
  let fixture: ComponentFixture<LigneCmdFournisseurComponent>;
  let service: LigneCmdFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LigneCmdFournisseurComponent],
    })
      .overrideTemplate(LigneCmdFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneCmdFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneCmdFournisseurService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ligneCmdFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
